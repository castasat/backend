package backend.routes

import backend.data.models.Region
import backend.services.RegionService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di


fun Routing.regionRoute() {
    val regionService: RegionService by di().instance()
    route("/region") {
        handleGetAllRegions(regionService = regionService)
        handlePostRegion(regionService = regionService)
        handleDeleteRegion(regionService = regionService)
        handleGetRegion(regionService = regionService)
    }
}

private fun Route.handleGetAllRegions(regionService: RegionService) {
    get {
        val allRegions = regionService.getAllRegions()
        if (allRegions.isNotEmpty()) {
            call.respond(allRegions)
        } else {
            call.respondText(
                text = "No regions found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostRegion(regionService: RegionService) {
    post {
        val region = call.receive<Region>()
        regionService.addRegion(region)
        call.respondText(
            text = "Region added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteRegion(regionService: RegionService) {
    delete("{regionId}") {
        val regionId = call.parameters["regionId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing regionId",
                status = HttpStatusCode.NotFound
            )
        try {
            regionService.deleteRegion(regionId)
            call.respondText(
                text = "Region deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Region with regionId = $regionId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetRegion(regionService: RegionService) {
    get("{regionId}") {
        val regionId = call.parameters["regionId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing regionId",
                status = HttpStatusCode.NotFound
            )
        try {
            val region = regionService.getRegion(regionId)
            call.respond(region)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Region with regionId = $regionId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
