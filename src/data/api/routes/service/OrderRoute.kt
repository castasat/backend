package backend.data.api.routes.service

import backend.data.api.models.service.Order
import backend.data.repositories.service.OrderRepository
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
fun Routing.orderRoute() {
    val orderRepository: OrderRepository by di().instance()
    route("/order") {
        handleGetAllOrders(orderRepository = orderRepository)
        handlePostOrder(orderRepository = orderRepository)
        handleDeleteOrder(orderRepository = orderRepository)
        handleGetOrder(orderRepository = orderRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllOrders(orderRepository: OrderRepository) {
    get {
        val allOrders = orderRepository.getAllOrders()
        if (allOrders.isNotEmpty()) {
            call.respond(allOrders)
        } else {
            call.respondText(
                text = "No orders found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostOrder(orderRepository: OrderRepository) {
    post {
        val order = call.receive<Order>()
        orderRepository.addOrder(order)
        call.respondText(
            text = "Order added",
            status = HttpStatusCode.Created
        )
    }
}

@ExperimentalSerializationApi
private fun Route.handleDeleteOrder(orderRepository: OrderRepository) {
    delete("{orderId}") {
        val orderId = call.parameters["orderId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing orderId",
                status = HttpStatusCode.NotFound
            )
        try {
            orderRepository.deleteOrder(orderId)
            call.respondText(
                text = "Order deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Order with orderId = $orderId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetOrder(orderRepository: OrderRepository) {
    get("{orderId}") {
        val orderId = call.parameters["orderId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing orderId",
                status = HttpStatusCode.NotFound
            )
        try {
            val order = orderRepository.getOrder(orderId)
            call.respond(order)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Order with orderId = $orderId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
