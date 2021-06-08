package backend.data.api.routes.service

import backend.data.api.models.service.ServiceType
import backend.data.repositories.service.ServiceTypeRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.serviceTypeRoute() {
    val serviceTypeRepository: ServiceTypeRepository by di().instance()
    route("/service_type") {
        handleGetAllServiceTypes(serviceTypeRepository = serviceTypeRepository)
        handlePostServiceType(serviceTypeRepository = serviceTypeRepository)
        handleDeleteServiceType(serviceTypeRepository = serviceTypeRepository)
        handleGetServiceType(serviceTypeRepository = serviceTypeRepository)
    }
}

private fun Route.handleGetAllServiceTypes(serviceTypeRepository: ServiceTypeRepository) {
    get {
        val allServiceTypes = serviceTypeRepository.getAllServiceTypes()
        if (allServiceTypes.isNotEmpty()) {
            call.respond(allServiceTypes)
        } else {
            call.respondText(
                text = "No serviceTypes found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostServiceType(serviceTypeRepository: ServiceTypeRepository) {
    post {
        val serviceType = call.receive<ServiceType>()
        serviceTypeRepository.addServiceType(serviceType)
        call.respondText(
            text = "ServiceType added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteServiceType(serviceTypeRepository: ServiceTypeRepository) {
    delete("{serviceTypeId}") {
        val serviceTypeId = call.parameters["serviceTypeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing serviceTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            serviceTypeRepository.deleteServiceType(serviceTypeId)
            call.respondText(
                text = "ServiceType deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "ServiceType with serviceTypeId = $serviceTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetServiceType(serviceTypeRepository: ServiceTypeRepository) {
    get("{serviceTypeId}") {
        val serviceTypeId = call.parameters["serviceTypeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing serviceTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val serviceType = serviceTypeRepository.getServiceType(serviceTypeId)
            call.respond(serviceType)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "ServiceType with serviceTypeId = $serviceTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
