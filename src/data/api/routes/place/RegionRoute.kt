package backend.data.api.routes.place

import backend.data.api.models.place.Region
import backend.data.repositories.place.RegionRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.regionRoute() {
    val regionRepository: RegionRepository by di().instance()
    route("/region") {
        handleGetAllRegions(regionRepository = regionRepository)
        handlePostRegion(regionRepository = regionRepository)
        handleDeleteRegion(regionRepository = regionRepository)
        handleGetRegion(regionRepository = regionRepository)
    }
}

private fun Route.handleGetAllRegions(regionRepository: RegionRepository) {
    get {
        val allRegions = regionRepository.getAllRegions()
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

private fun Route.handlePostRegion(regionRepository: RegionRepository) {
    post {
        val region = call.receive<Region>()
        regionRepository.addRegion(region)
        call.respondText(
            text = "Region added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteRegion(regionRepository: RegionRepository) {
    delete("{regionId}") {
        val regionId = call.parameters["regionId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing regionId",
                status = HttpStatusCode.NotFound
            )
        try {
            regionRepository.deleteRegion(regionId)
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

private fun Route.handleGetRegion(regionRepository: RegionRepository) {
    get("{regionId}") {
        val regionId = call.parameters["regionId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing regionId",
                status = HttpStatusCode.NotFound
            )
        try {
            val region = regionRepository.getRegion(regionId)
            call.respond(region)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Region with regionId = $regionId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
