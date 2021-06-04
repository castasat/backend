package backend.data.api.routes

import backend.data.api.models.Gender
import backend.data.repositories.GenderRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.genderRoute() {
    val genderRepository: GenderRepository by di().instance()
    route("/gender") {
        handleGetAllGenders(genderRepository = genderRepository)
        handlePostGender(genderRepository = genderRepository)
        handleDeleteGender(genderRepository = genderRepository)
        handleGetGender(genderRepository = genderRepository)
    }
}

private fun Route.handleGetAllGenders(genderRepository: GenderRepository) {
    get {
        val allGenders = genderRepository.getAllGenders()
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

private fun Route.handlePostGender(genderRepository: GenderRepository) {
    post {
        val gender = call.receive<Gender>()
        genderRepository.addGender(gender)
        call.respondText(
            text = "Gender added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteGender(genderRepository: GenderRepository) {
    delete("{genderId}") {
        val genderId = call.parameters["genderId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing genderId",
                status = HttpStatusCode.NotFound
            )
        try {
            genderRepository.deleteGender(genderId)
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

private fun Route.handleGetGender(genderRepository: GenderRepository) {
    get("{genderId}") {
        val genderId = call.parameters["genderId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing genderId",
                status = HttpStatusCode.NotFound
            )
        try {
            val gender = genderRepository.getGender(genderId)
            call.respond(gender)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Gender with genderId = $genderId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
