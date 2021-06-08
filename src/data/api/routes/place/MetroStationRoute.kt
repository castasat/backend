package backend.data.api.routes.place

import backend.data.api.models.place.MetroStation
import backend.data.repositories.place.MetroStationRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.metroStationRoute() {
    val metroStationRepository: MetroStationRepository by di().instance()
    route("/metro_station") {
        handleGetAllMetroStations(metroStationRepository = metroStationRepository)
        handlePostMetroStation(metroStationRepository = metroStationRepository)
        handleDeleteMetroStation(metroStationRepository = metroStationRepository)
        handleGetMetroStation(metroStationRepository = metroStationRepository)
    }
}

private fun Route.handleGetAllMetroStations(metroStationRepository: MetroStationRepository) {
    get {
        val allMetroStations = metroStationRepository.getAllMetroStations()
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

private fun Route.handlePostMetroStation(metroStationRepository: MetroStationRepository) {
    post {
        val metroStation = call.receive<MetroStation>()
        metroStationRepository.addMetroStation(metroStation)
        call.respondText(
            text = "MetroStation added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteMetroStation(metroStationRepository: MetroStationRepository) {
    delete("{metroStationId}") {
        val metroStationId = call.parameters["metroStationId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing metroStationId",
                status = HttpStatusCode.NotFound
            )
        try {
            metroStationRepository.deleteMetroStation(metroStationId)
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

private fun Route.handleGetMetroStation(metroStationRepository: MetroStationRepository) {
    get("{metroStationId}") {
        val metroStationId = call.parameters["metroStationId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing metroStationId",
                status = HttpStatusCode.NotFound
            )
        try {
            val metroStation = metroStationRepository.getMetroStation(metroStationId)
            call.respond(metroStation)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "MetroStation with metroStationId = $metroStationId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
