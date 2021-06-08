package backend.data.api.routes.service

import backend.data.api.models.service.Service
import backend.data.repositories.service.ServiceRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.serviceRoute() {
    val serviceRepository: ServiceRepository by di().instance()
    route("/service") {
        handleGetAllServices(serviceRepository = serviceRepository)
        handlePostService(serviceRepository = serviceRepository)
        handleDeleteService(serviceRepository = serviceRepository)
        handleGetService(serviceRepository = serviceRepository)
    }
}

private fun Route.handleGetAllServices(serviceRepository: ServiceRepository) {
    get {
        val allServices = serviceRepository.getAllServices()
        if (allServices.isNotEmpty()) {
            call.respond(allServices)
        } else {
            call.respondText(
                text = "No services found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostService(serviceRepository: ServiceRepository) {
    post {
        val service = call.receive<Service>()
        serviceRepository.addService(service)
        call.respondText(
            text = "Service added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteService(serviceRepository: ServiceRepository) {
    delete("{serviceId}") {
        val serviceId = call.parameters["serviceId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing serviceId",
                status = HttpStatusCode.NotFound
            )
        try {
            serviceRepository.deleteService(serviceId)
            call.respondText(
                text = "Service deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Service with serviceId = $serviceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetService(serviceRepository: ServiceRepository) {
    get("{serviceId}") {
        val serviceId = call.parameters["serviceId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing serviceId",
                status = HttpStatusCode.NotFound
            )
        try {
            val service = serviceRepository.getService(serviceId)
            call.respond(service)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Service with serviceId = $serviceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
