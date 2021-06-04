package backend.data.api.routes

import backend.data.api.models.Coordinates
import backend.data.repositories.CoordinatesRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.coordinatesRoute() {
    val coordinatesRepository: CoordinatesRepository by di().instance()
    route("/coordinates") {
        handleGetAllCoordinates(coordinatesRepository = coordinatesRepository)
        handlePostCoordinates(coordinatesRepository = coordinatesRepository)
        handleDeleteCoordinates(coordinatesRepository = coordinatesRepository)
        handleGetCoordinates(coordinatesRepository = coordinatesRepository)
    }
}

private fun Route.handleGetAllCoordinates(coordinatesRepository: CoordinatesRepository) {
    get {
        val allCoordinates = coordinatesRepository.getAllCoordinates()
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

private fun Route.handlePostCoordinates(coordinatesRepository: CoordinatesRepository) {
    post {
        val coordinates = call.receive<Coordinates>()
        coordinatesRepository.addCoordinates(coordinates)
        call.respondText(
            text = "Coordinates added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteCoordinates(coordinatesRepository: CoordinatesRepository) {
    delete("{coordinatesId}") {
        val coordinatesId = call.parameters["coordinatesId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing coordinatesId",
                status = HttpStatusCode.NotFound
            )
        try {
            coordinatesRepository.deleteCoordinates(coordinatesId)
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

private fun Route.handleGetCoordinates(coordinatesRepository: CoordinatesRepository) {
    get("{coordinatesId}") {
        val coordinatesId = call.parameters["coordinatesId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing coordinatesId",
                status = HttpStatusCode.NotFound
            )
        try {
            val coordinates = coordinatesRepository.getCoordinates(coordinatesId)
            call.respond(coordinates)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Coordinates with coordinatesId = $coordinatesId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}