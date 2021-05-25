package backend.routes

import backend.data.models.Gender
import backend.services.GenderService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.genderRoute() {
    val genderService: GenderService by di().instance()
    route("/gender") {
        handleGetAllGenders(genderService = genderService)
        handlePostGender(genderService = genderService)
        handleDeleteGender(genderService = genderService)
        handleGetGender(genderService = genderService)
    }
}

private fun Route.handleGetAllGenders(genderService: GenderService) {
    get {
        val allGenders = genderService.getAllGenders()
        if (allGenders.isNotEmpty()) {
            call.respond(allGenders)
        } else {
            call.respondText(
                text = "No genders found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostGender(genderService: GenderService) {
    post {
        val gender = call.receive<Gender>()
        genderService.addGender(gender)
        call.respondText(
            text = "Gender added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteGender(genderService: GenderService) {
    delete("{genderId}") {
        val genderId = call.parameters["genderId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing genderId",
                status = HttpStatusCode.NotFound
            )
        try {
            genderService.deleteGender(genderId)
            call.respondText(
                text = "Gender deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Gender with genderId = $genderId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetGender(genderService: GenderService) {
    get("{genderId}") {
        val genderId = call.parameters["genderId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing genderId",
                status = HttpStatusCode.NotFound
            )
        try {
            val gender = genderService.getGender(genderId)
            call.respond(gender)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Gender with genderId = $genderId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
