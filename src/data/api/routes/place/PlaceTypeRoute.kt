package backend.data.api.routes.place

import backend.data.api.models.place.PlaceType
import backend.data.repositories.place.PlaceTypeRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.placeTypeRoute() {
    val placeTypeRepository: PlaceTypeRepository by di().instance()
    route("/place_type") {
        handleGetAllPlaceTypes(placeTypeRepository = placeTypeRepository)
        handlePostPlaceType(placeTypeRepository = placeTypeRepository)
        handleDeletePlaceType(placeTypeRepository = placeTypeRepository)
        handleGetPlaceType(placeTypeRepository = placeTypeRepository)
    }
}

private fun Route.handleGetAllPlaceTypes(placeTypeRepository: PlaceTypeRepository) {
    get {
        val allPlaceTypes = placeTypeRepository.getAllPlaceTypes()
        if (allPlaceTypes.isNotEmpty()) {
            call.respond(allPlaceTypes)
        } else {
            call.respondText(
                text = "No placeTypes found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostPlaceType(placeTypeRepository: PlaceTypeRepository) {
    post {
        val placeType = call.receive<PlaceType>()
        placeTypeRepository.addPlaceType(placeType)
        call.respondText(
            text = "PlaceType added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeletePlaceType(placeTypeRepository: PlaceTypeRepository) {
    delete("{placeTypeId}") {
        val placeTypeId = call.parameters["placeTypeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing placeTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            placeTypeRepository.deletePlaceType(placeTypeId)
            call.respondText(
                text = "PlaceType deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "PlaceType with placeTypeId = $placeTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetPlaceType(placeTypeRepository: PlaceTypeRepository) {
    get("{placeTypeId}") {
        val placeTypeId = call.parameters["placeTypeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing placeTypeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val placeType = placeTypeRepository.getPlaceType(placeTypeId)
            call.respond(placeType)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "PlaceType with placeTypeId = $placeTypeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
