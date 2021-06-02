package backend.routes.address

import backend.data.models.address.Building
import backend.services.address.BuildingService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.buildingRoute() {
    val buildingService: BuildingService by di().instance()
    route("/building") {
        handleGetAllBuildings(buildingService = buildingService)
        handlePostBuilding(buildingService = buildingService)
        handleDeleteBuilding(buildingService = buildingService)
        handleGetBuilding(buildingService = buildingService)
    }
}

private fun Route.handleGetAllBuildings(buildingService: BuildingService) {
    get {
        val allBuildings = buildingService.getAllBuildings()
        if (allBuildings.isNotEmpty()) {
            call.respond(allBuildings)
        } else {
            call.respondText(
                text = "No buildings found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostBuilding(buildingService: BuildingService) {
    post {
        val building = call.receive<Building>()
        buildingService.addBuilding(building)
        call.respondText(
            text = "Building added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteBuilding(buildingService: BuildingService) {
    delete("{buildingId}") {
        val buildingId = call.parameters["buildingId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing buildingId",
                status = HttpStatusCode.NotFound
            )
        try {
            buildingService.deleteBuilding(buildingId)
            call.respondText(
                text = "Building deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Building with buildingId = $buildingId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetBuilding(buildingService: BuildingService) {
    get("{buildingId}") {
        val buildingId = call.parameters["buildingId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing buildingId",
                status = HttpStatusCode.NotFound
            )
        try {
            val building = buildingService.getBuilding(buildingId)
            call.respond(building)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Building with buildingId = $buildingId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
