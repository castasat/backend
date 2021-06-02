package backend.routes.address

import backend.data.models.address.Locality
import backend.services.address.LocalityService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.localityRoute() {
    val localityService: LocalityService by di().instance()
    route("/locality") {
        handleGetAllLocalities(localityService = localityService)
        handlePostLocality(localityService = localityService)
        handleDeleteLocality(localityService = localityService)
        handleGetLocality(localityService = localityService)
    }
}

private fun Route.handleGetAllLocalities(localityService: LocalityService) {
    get {
        val allLocalities = localityService.getAllLocalities()
        if (allLocalities.isNotEmpty()) {
            call.respond(allLocalities)
        } else {
            call.respondText(
                text = "No localities found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostLocality(localityService: LocalityService) {
    post {
        val locality = call.receive<Locality>()
        localityService.addLocality(locality)
        call.respondText(
            text = "Locality added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLocality(localityService: LocalityService) {
    delete("{localityId}") {
        val localityId = call.parameters["localityId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing localityId",
                status = HttpStatusCode.NotFound
            )
        try {
            localityService.deleteLocality(localityId)
            call.respondText(
                text = "Locality deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Locality with localityId = $localityId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetLocality(localityService: LocalityService) {
    get("{localityId}") {
        val localityId = call.parameters["localityId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing localityId",
                status = HttpStatusCode.NotFound
            )
        try {
            val locality = localityService.getLocality(localityId)
            call.respond(locality)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Locality with localityId = $localityId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
