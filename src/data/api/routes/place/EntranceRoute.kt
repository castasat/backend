package backend.data.api.routes.place

import backend.data.api.models.place.Entrance
import backend.data.repositories.place.EntranceRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.entranceRoute() {
    val entranceRepository: EntranceRepository by di().instance()
    route("/entrance") {
        handleGetAllEntrances(entranceRepository = entranceRepository)
        handlePostEntrance(entranceRepository = entranceRepository)
        handleDeleteEntrance(entranceRepository = entranceRepository)
        handleGetEntrance(entranceRepository = entranceRepository)
    }
}

private fun Route.handleGetAllEntrances(entranceRepository: EntranceRepository) {
    get {
        val allEntrances = entranceRepository.getAllEntrances()
        if (allEntrances.isNotEmpty()) {
            call.respond(allEntrances)
        } else {
            call.respondText(
                text = "No entrances found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostEntrance(entranceRepository: EntranceRepository) {
    post {
        val entrance = call.receive<Entrance>()
        entranceRepository.addEntrance(entrance)
        call.respondText(
            text = "Entrance added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteEntrance(entranceRepository: EntranceRepository) {
    delete("{entranceId}") {
        val entranceId = call.parameters["entranceId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing entranceId",
                status = HttpStatusCode.NotFound
            )
        try {
            entranceRepository.deleteEntrance(entranceId)
            call.respondText(
                text = "Entrance deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Entrance with entranceId = $entranceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetEntrance(entranceRepository: EntranceRepository) {
    get("{entranceId}") {
        val entranceId = call.parameters["entranceId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing entranceId",
                status = HttpStatusCode.NotFound
            )
        try {
            val entrance = entranceRepository.getEntrance(entranceId)
            call.respond(entrance)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Entrance with entranceId = $entranceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
