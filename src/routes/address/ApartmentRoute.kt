package backend.routes.address

import backend.data.models.address.Apartment
import backend.services.address.ApartmentService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.apartmentRoute() {
    val apartmentService: ApartmentService by di().instance()
    route("/apartment") {
        handleGetAllApartments(apartmentService = apartmentService)
        handlePostApartment(apartmentService = apartmentService)
        handleDeleteApartment(apartmentService = apartmentService)
        handleGetApartment(apartmentService = apartmentService)
    }
}

private fun Route.handleGetAllApartments(apartmentService: ApartmentService) {
    get {
        val allApartments = apartmentService.getAllApartments()
        if (allApartments.isNotEmpty()) {
            call.respond(allApartments)
        } else {
            call.respondText(
                text = "No apartments found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostApartment(apartmentService: ApartmentService) {
    post {
        val apartment = call.receive<Apartment>()
        apartmentService.addApartment(apartment)
        call.respondText(
            text = "Apartment added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteApartment(apartmentService: ApartmentService) {
    delete("{apartmentId}") {
        val apartmentId = call.parameters["apartmentId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing apartmentId",
                status = HttpStatusCode.NotFound
            )
        try {
            apartmentService.deleteApartment(apartmentId)
            call.respondText(
                text = "Apartment deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Apartment with apartmentId = $apartmentId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetApartment(apartmentService: ApartmentService) {
    get("{apartmentId}") {
        val apartmentId = call.parameters["apartmentId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing apartmentId",
                status = HttpStatusCode.NotFound
            )
        try {
            val apartment = apartmentService.getApartment(apartmentId)
            call.respond(apartment)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Apartment with apartmentId = $apartmentId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
