package backend.data.api.routes.payment

import backend.data.api.models.payment.Payment
import backend.data.repositories.payment.PaymentRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

@ExperimentalSerializationApi
fun Routing.paymentRoute() {
    val paymentRepository: PaymentRepository by di().instance()
    route("/payment") {
        handleGetAllPayments(paymentRepository = paymentRepository)
        handlePostPayment(paymentRepository = paymentRepository)
        handleDeletePayment(paymentRepository = paymentRepository)
        handleGetPayment(paymentRepository = paymentRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllPayments(paymentRepository: PaymentRepository) {
    get {
        val allPayments = paymentRepository.getAllPayments()
        if (allPayments.isNotEmpty()) {
            call.respond(allPayments)
        } else {
            call.respondText(
                text = "No payments found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostPayment(paymentRepository: PaymentRepository) {
    post {
        val payment = call.receive<Payment>()
        paymentRepository.addPayment(payment)
        call.respondText(
            text = "Payment added",
            status = HttpStatusCode.Created
        )
    }
}

@ExperimentalSerializationApi
private fun Route.handleDeletePayment(paymentRepository: PaymentRepository) {
    delete("{paymentId}") {
        val paymentId = call.parameters["paymentId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing paymentId",
                status = HttpStatusCode.NotFound
            )
        try {
            paymentRepository.deletePayment(paymentId)
            call.respondText(
                text = "Payment deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Payment with paymentId = $paymentId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetPayment(paymentRepository: PaymentRepository) {
    get("{paymentId}") {
        val paymentId = call.parameters["paymentId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing paymentId",
                status = HttpStatusCode.NotFound
            )
        try {
            val payment = paymentRepository.getPayment(paymentId)
            call.respond(payment)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Payment with paymentId = $paymentId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}