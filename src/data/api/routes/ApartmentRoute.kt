package backend.data.api.routes

import backend.data.repositories.ApartmentRepository
import backend.data.api.models.Apartment
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.apartmentRoute() {
    val apartmentRepository: ApartmentRepository by di().instance()
    route("/apartment") {
        handleGetAllApartments(apartmentRepository = apartmentRepository)
        handlePostApartment(apartmentRepository = apartmentRepository)
        handleDeleteApartment(apartmentRepository = apartmentRepository)
        handleGetApartment(apartmentRepository = apartmentRepository)
    }
}

private fun Route.handleGetAllApartments(apartmentRepository: ApartmentRepository) {
    get {
        val allApartments = apartmentRepository.getAllApartments()
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

private fun Route.handlePostApartment(apartmentRepository: ApartmentRepository) {
    post {
        val apartment = call.receive<Apartment>()
        apartmentRepository.addApartment(apartment)
        call.respondText(
            text = "Apartment added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteApartment(apartmentRepository: ApartmentRepository) {
    delete("{apartmentId}") {
        val apartmentId = call.parameters["apartmentId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing apartmentId",
                status = HttpStatusCode.NotFound
            )
        try {
            apartmentRepository.deleteApartment(apartmentId)
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

private fun Route.handleGetApartment(apartmentRepository: ApartmentRepository) {
    get("{apartmentId}") {
        val apartmentId = call.parameters["apartmentId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing apartmentId",
                status = HttpStatusCode.NotFound
            )
        try {
            val apartment = apartmentRepository.getApartment(apartmentId)
            call.respond(apartment)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Apartment with apartmentId = $apartmentId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
