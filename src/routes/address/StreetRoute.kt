package backend.routes.address

import backend.data.models.address.Street
import backend.services.address.StreetService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.streetRoute() {
    val streetService: StreetService by di().instance()
    route("/street") {
        handleGetAllStreets(streetService = streetService)
        handlePostStreet(streetService = streetService)
        handleDeleteStreet(streetService = streetService)
        handleGetStreet(streetService = streetService)
    }
}

private fun Route.handleGetAllStreets(streetService: StreetService) {
    get {
        val allStreets = streetService.getAllStreets()
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

private fun Route.handlePostStreet(streetService: StreetService) {
    post {
        val street = call.receive<Street>()
        streetService.addStreet(street)
        call.respondText(
            text = "Street added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteStreet(streetService: StreetService) {
    delete("{streetId}") {
        val streetId = call.parameters["streetId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing streetId",
                status = HttpStatusCode.NotFound
            )
        try {
            streetService.deleteStreet(streetId)
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

private fun Route.handleGetStreet(streetService: StreetService) {
    get("{streetId}") {
        val streetId = call.parameters["streetId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing streetId",
                status = HttpStatusCode.NotFound
            )
        try {
            val street = streetService.getStreet(streetId)
            call.respond(street)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Street with streetId = $streetId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
