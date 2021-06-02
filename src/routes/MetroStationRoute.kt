package backend.routes

import backend.data.models.MetroStation
import backend.services.MetroStationService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.metroStationRoute() {
    val metroStationService: MetroStationService by di().instance()
    route("/metro_station") {
        handleGetAllMetroStations(metroStationService = metroStationService)
        handlePostMetroStation(metroStationService = metroStationService)
        handleDeleteMetroStation(metroStationService = metroStationService)
        handleGetMetroStation(metroStationService = metroStationService)
    }
}

private fun Route.handleGetAllMetroStations(metroStationService: MetroStationService) {
    get {
        val allMetroStations = metroStationService.getAllMetroStations()
        if (allMetroStations.isNotEmpty()) {
            call.respond(allMetroStations)
        } else {
            call.respondText(
                text = "No metroStations found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostMetroStation(metroStationService: MetroStationService) {
    post {
        val metroStation = call.receive<MetroStation>()
        metroStationService.addMetroStation(metroStation)
        call.respondText(
            text = "MetroStation added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteMetroStation(metroStationService: MetroStationService) {
    delete("{metroStationId}") {
        val metroStationId = call.parameters["metroStationId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing metroStationId",
                status = HttpStatusCode.NotFound
            )
        try {
            metroStationService.deleteMetroStation(metroStationId)
            call.respondText(
                text = "MetroStation deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "MetroStation with metroStationId = $metroStationId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetMetroStation(metroStationService: MetroStationService) {
    get("{metroStationId}") {
        val metroStationId = call.parameters["metroStationId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing metroStationId",
                status = HttpStatusCode.NotFound
            )
        try {
            val metroStation = metroStationService.getMetroStation(metroStationId)
            call.respond(metroStation)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "MetroStation with metroStationId = $metroStationId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
