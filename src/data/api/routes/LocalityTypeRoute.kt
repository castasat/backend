package backend.data.api.routes

import backend.data.api.models.LocalityType
import backend.data.repositories.LocalityTypeRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.localityTypeRoute() {
    val localityTypeRepository: LocalityTypeRepository by di().instance()
    route("/locality_type") {
        handleGetAllLocalityTypes(localityTypeRepository = localityTypeRepository)
        handlePostLocalityType(localityTypeRepository = localityTypeRepository)
        handleDeleteLocalityType(localityTypeRepository = localityTypeRepository)
        handleGetLocalityType(localityTypeRepository = localityTypeRepository)
    }
}

private fun Route.handleGetAllLocalityTypes(localityTypeRepository: LocalityTypeRepository) {
    get {
        val allLocalityTypes = localityTypeRepository.getAllLocalityTypes()
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

private fun Route.handlePostLocalityType(localityTypeRepository: LocalityTypeRepository) {
    post {
        val localityType = call.receive<LocalityType>()
        localityTypeRepository.addLocalityType(localityType)
        call.respondText(
            text = "LocalityType added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLocalityType(localityTypeRepository: LocalityTypeRepository) {
    delete("{localityTypeId}") {
        val localityTypeId = call.parameters["localityTypeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing localityTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            localityTypeRepository.deleteLocalityType(localityTypeId)
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

private fun Route.handleGetLocalityType(localityTypeRepository: LocalityTypeRepository) {
    get("{localityTypeId}") {
        val localityTypeId = call.parameters["localityTypeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing localityTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val localityType = localityTypeRepository.getLocalityType(localityTypeId)
            call.respond(localityType)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "LocalityType with localityTypeId = $localityTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
