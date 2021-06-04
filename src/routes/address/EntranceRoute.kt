package backend.routes.address

import backend.data.models.address.Entrance
import backend.services.address.EntranceService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.entranceRoute() {
    val entranceService: EntranceService by di().instance()
    route("/entrance") {
        handleGetAllEntrances(entranceService = entranceService)
        handlePostEntrance(entranceService = entranceService)
        handleDeleteEntrance(entranceService = entranceService)
        handleGetEntrance(entranceService = entranceService)
    }
}

private fun Route.handleGetAllEntrances(entranceService: EntranceService) {
    get {
        val allEntrances = entranceService.getAllEntrances()
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

private fun Route.handlePostEntrance(entranceService: EntranceService) {
    post {
        val entrance = call.receive<Entrance>()
        entranceService.addEntrance(entrance)
        call.respondText(
            text = "Entrance added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteEntrance(entranceService: EntranceService) {
    delete("{entranceId}") {
        val entranceId = call.parameters["entranceId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing entranceId",
                status = HttpStatusCode.NotFound
            )
        try {
            entranceService.deleteEntrance(entranceId)
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

private fun Route.handleGetEntrance(entranceService: EntranceService) {
    get("{entranceId}") {
        val entranceId = call.parameters["entranceId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing entranceId",
                status = HttpStatusCode.NotFound
            )
        try {
            val entrance = entranceService.getEntrance(entranceId)
            call.respond(entrance)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Entrance with entranceId = $entranceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
