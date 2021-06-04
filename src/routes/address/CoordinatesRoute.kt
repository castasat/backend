package backend.routes.address

import backend.data.models.address.Coordinates
import backend.services.address.CoordinatesService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.coordinatesRoute() {
    val coordinatesService: CoordinatesService by di().instance()
    route("/coordinates") {
        handleGetAllCoordinates(coordinatesService = coordinatesService)
        handlePostCoordinates(coordinatesService = coordinatesService)
        handleDeleteCoordinates(coordinatesService = coordinatesService)
        handleGetCoordinates(coordinatesService = coordinatesService)
    }
}

private fun Route.handleGetAllCoordinates(coordinatesService: CoordinatesService) {
    get {
        val allCoordinates = coordinatesService.getAllCoordinates()
        if (allCoordinates.isNotEmpty()) {
            call.respond(allCoordinates)
        } else {
            call.respondText(
                text = "No coordinates found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostCoordinates(coordinatesService: CoordinatesService) {
    post {
        val coordinates = call.receive<Coordinates>()
        coordinatesService.addCoordinates(coordinates)
        call.respondText(
            text = "Coordinates added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteCoordinates(coordinatesService: CoordinatesService) {
    delete("{coordinatesId}") {
        val coordinatesId = call.parameters["coordinatesId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing coordinatesId",
                status = HttpStatusCode.NotFound
            )
        try {
            coordinatesService.deleteCoordinates(coordinatesId)
            call.respondText(
                text = "Coordinates deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Coordinates with coordinatesId = $coordinatesId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetCoordinates(coordinatesService: CoordinatesService) {
    get("{coordinatesId}") {
        val coordinatesId = call.parameters["coordinatesId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing coordinatesId",
                status = HttpStatusCode.NotFound
            )
        try {
            val coordinates = coordinatesService.getCoordinates(coordinatesId)
            call.respond(coordinates)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Coordinates with coordinatesId = $coordinatesId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}