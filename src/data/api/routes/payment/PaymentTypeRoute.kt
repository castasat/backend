package backend.data.api.routes.payment

import backend.data.api.models.payment.PaymentType
import backend.data.repositories.payment.PaymentTypeRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.paymentTypeRoute() {
    val paymentTypeRepository: PaymentTypeRepository by di().instance()
    route("/payment_type") {
        handleGetAllPaymentTypes(paymentTypeRepository = paymentTypeRepository)
        handlePostPaymentType(paymentTypeRepository = paymentTypeRepository)
        handleDeletePaymentType(paymentTypeRepository = paymentTypeRepository)
        handleGetPaymentType(paymentTypeRepository = paymentTypeRepository)
    }
}

private fun Route.handleGetAllPaymentTypes(paymentTypeRepository: PaymentTypeRepository) {
    get {
        val allPaymentTypes = paymentTypeRepository.getAllPaymentTypes()
        if (allPaymentTypes.isNotEmpty()) {
            call.respond(allPaymentTypes)
        } else {
            call.respondText(
                text = "No paymentTypes found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostPaymentType(paymentTypeRepository: PaymentTypeRepository) {
    post {
        val paymentType = call.receive<PaymentType>()
        paymentTypeRepository.addPaymentType(paymentType)
        call.respondText(
            text = "PaymentType added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeletePaymentType(paymentTypeRepository: PaymentTypeRepository) {
    delete("{paymentTypeId}") {
        val paymentTypeId = call.parameters["paymentTypeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing paymentTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            paymentTypeRepository.deletePaymentType(paymentTypeId)
            call.respondText(
                text = "PaymentType deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "PaymentType with paymentTypeId = $paymentTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetPaymentType(paymentTypeRepository: PaymentTypeRepository) {
    get("{paymentTypeId}") {
        val paymentTypeId = call.parameters["paymentTypeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing paymentTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val paymentType = paymentTypeRepository.getPaymentType(paymentTypeId)
            call.respond(paymentType)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "PaymentType with paymentTypeId = $paymentTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
