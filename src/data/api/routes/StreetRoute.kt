package backend.data.api.routes

import backend.data.api.models.Street
import backend.data.repositories.StreetRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.streetRoute() {
    val streetRepository: StreetRepository by di().instance()
    route("/street") {
        handleGetAllStreets(streetRepository = streetRepository)
        handlePostStreet(streetRepository = streetRepository)
        handleDeleteStreet(streetRepository = streetRepository)
        handleGetStreet(streetRepository = streetRepository)
    }
}

private fun Route.handleGetAllStreets(streetRepository: StreetRepository) {
    get {
        val allStreets = streetRepository.getAllStreets()
        if (allStreets.isNotEmpty()) {
            call.respond(allStreets)
        } else {
            call.respondText(
                text = "No streets found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostStreet(streetRepository: StreetRepository) {
    post {
        val street = call.receive<Street>()
        streetRepository.addStreet(street)
        call.respondText(
            text = "Street added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteStreet(streetRepository: StreetRepository) {
    delete("{streetId}") {
        val streetId = call.parameters["streetId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing streetId",
                status = HttpStatusCode.NotFound
            )
        try {
            streetRepository.deleteStreet(streetId)
            call.respondText(
                text = "Street deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Street with streetId = $streetId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetStreet(streetRepository: StreetRepository) {
    get("{streetId}") {
        val streetId = call.parameters["streetId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing streetId",
                status = HttpStatusCode.NotFound
            )
        try {
            val street = streetRepository.getStreet(streetId)
            call.respond(street)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Street with streetId = $streetId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
