package backend.routes.address

import backend.data.models.address.LocalityType
import backend.services.address.LocalityTypeService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.localityTypeRoute() {
    val localityTypeService: LocalityTypeService by di().instance()
    route("/locality_type") {
        handleGetAllLocalityTypes(localityTypeService = localityTypeService)
        handlePostLocalityType(localityTypeService = localityTypeService)
        handleDeleteLocalityType(localityTypeService = localityTypeService)
        handleGetLocalityType(localityTypeService = localityTypeService)
    }
}

private fun Route.handleGetAllLocalityTypes(localityTypeService: LocalityTypeService) {
    get {
        val allLocalityTypes = localityTypeService.getAllLocalityTypes()
        if (allLocalityTypes.isNotEmpty()) {
            call.respond(allLocalityTypes)
        } else {
            call.respondText(
                text = "No localityTypes found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostLocalityType(localityTypeService: LocalityTypeService) {
    post {
        val localityType = call.receive<LocalityType>()
        localityTypeService.addLocalityType(localityType)
        call.respondText(
            text = "LocalityType added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLocalityType(localityTypeService: LocalityTypeService) {
    delete("{localityTypeId}") {
        val localityTypeId = call.parameters["localityTypeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing localityTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            localityTypeService.deleteLocalityType(localityTypeId)
            call.respondText(
                text = "LocalityType deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "LocalityType with localityTypeId = $localityTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetLocalityType(localityTypeService: LocalityTypeService) {
    get("{localityTypeId}") {
        val localityTypeId = call.parameters["localityTypeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing localityTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val localityType = localityTypeService.getLocalityType(localityTypeId)
            call.respond(localityType)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "LocalityType with localityTypeId = $localityTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
