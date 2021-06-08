package backend.data.api.routes.place

import backend.data.api.models.place.Locality
import backend.data.repositories.place.LocalityRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.localityRoute() {
    val localityRepository: LocalityRepository by di().instance()
    route("/locality") {
        handleGetAllLocalities(localityRepository = localityRepository)
        handlePostLocality(localityRepository = localityRepository)
        handleDeleteLocality(localityRepository = localityRepository)
        handleGetLocality(localityRepository = localityRepository)
    }
}

private fun Route.handleGetAllLocalities(localityRepository: LocalityRepository) {
    get {
        val allLocalities = localityRepository.getAllLocalities()
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

private fun Route.handlePostLocality(localityRepository: LocalityRepository) {
    post {
        val locality = call.receive<Locality>()
        localityRepository.addLocality(locality)
        call.respondText(
            text = "Locality added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLocality(localityRepository: LocalityRepository) {
    delete("{localityId}") {
        val localityId = call.parameters["localityId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing localityId",
                status = HttpStatusCode.NotFound
            )
        try {
            localityRepository.deleteLocality(localityId)
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

private fun Route.handleGetLocality(localityRepository: LocalityRepository) {
    get("{localityId}") {
        val localityId = call.parameters["localityId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing localityId",
                status = HttpStatusCode.NotFound
            )
        try {
            val locality = localityRepository.getLocality(localityId)
            call.respond(locality)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Locality with localityId = $localityId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
