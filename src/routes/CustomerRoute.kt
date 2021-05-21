package backend.routes

import backend.models.Customer
import backend.services.CustomerService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.customerRoute() {
    route("/") {
        get {
            call.respondText(
                "Hello, Backend on Kotlin powered by Ktor on Tomcat!",
                status = HttpStatusCode.OK
            )
        }

        val customerService: CustomerService by di().instance()

        route("/customer") {
            handleGetAllCustomers(customerService = customerService)
            handlePostCustomer(customerService = customerService)
            handleDeleteCustomer(customerService = customerService)
            handleGetCustomer(customerService = customerService)
        }
    }
}

fun Route.handleGetAllCustomers(customerService: CustomerService) {
    get {
        val allCustomers = customerService.getAllCustomers()
        if (allCustomers.isNotEmpty()) {
            call.respond(allCustomers)
        } else {
            call.respondText(
                text = "No customers found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

fun Route.handlePostCustomer(customerService: CustomerService) {
    post {
        val customer = call.receive<Customer>()
        customerService.addCustomer(customer)
        call.respondText(
            text = "Customer added",
            status = HttpStatusCode.Created
        )
    }
}

fun Route.handleDeleteCustomer(customerService: CustomerService) {
    delete("{id}") {
        val customerId = call.parameters["id"]
            ?.toIntOrNull()
            ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.NotFound
            )
        try {
            customerService.deleteCustomer(customerId)
            call.respondText(
                text = "Customer deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Customer with id = $customerId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

fun Route.handleGetCustomer(customerService: CustomerService) {
    get("{id}") {
        val customerId = call.parameters["id"]
            ?.toIntOrNull()
            ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.NotFound
            )
        try {
            val customer = customerService.getCustomer(customerId)
            call.respond(customer)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Customer with id = $customerId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}