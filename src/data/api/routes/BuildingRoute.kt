package backend.data.api.routes

import backend.data.api.models.Building
import backend.data.repositories.BuildingRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.buildingRoute() {
    val buildingRepository: BuildingRepository by di().instance()
    route("/building") {
        handleGetAllBuildings(buildingRepository = buildingRepository)
        handlePostBuilding(buildingRepository = buildingRepository)
        handleDeleteBuilding(buildingRepository = buildingRepository)
        handleGetBuilding(buildingRepository = buildingRepository)
    }
}

private fun Route.handleGetAllBuildings(buildingRepository: BuildingRepository) {
    get {
        val allBuildings = buildingRepository.getAllBuildings()
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

private fun Route.handlePostBuilding(buildingRepository: BuildingRepository) {
    post {
        val building = call.receive<Building>()
        buildingRepository.addBuilding(building)
        call.respondText(
            text = "Building added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteBuilding(buildingRepository: BuildingRepository) {
    delete("{buildingId}") {
        val buildingId = call.parameters["buildingId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing buildingId",
                status = HttpStatusCode.NotFound
            )
        try {
            buildingRepository.deleteBuilding(buildingId)
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

private fun Route.handleGetBuilding(buildingRepository: BuildingRepository) {
    get("{buildingId}") {
        val buildingId = call.parameters["buildingId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing buildingId",
                status = HttpStatusCode.NotFound
            )
        try {
            val building = buildingRepository.getBuilding(buildingId)
            call.respond(building)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Building with buildingId = $buildingId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
