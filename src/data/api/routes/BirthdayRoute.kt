package backend.data.api.routes

import backend.data.api.models.Birthday
import backend.data.repositories.BirthdayRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

@ExperimentalSerializationApi
fun Routing.birthdayRoute() {
    val birthdayRepository: BirthdayRepository by di().instance()
    route("/birthday") {
        handleGetAllBirthdays(birthdayRepository = birthdayRepository)
        handlePostBirthday(birthdayRepository = birthdayRepository)
        handleDeleteBirthday(birthdayRepository = birthdayRepository)
        handleGetBirthday(birthdayRepository = birthdayRepository)
    }
}

private fun Route.handleGetAllBirthdays(birthdayRepository: BirthdayRepository) {
    get {
        val allBirthdays = birthdayRepository.getAllBirthdays()
        if (allBirthdays.isNotEmpty()) {
            call.respond(allBirthdays)
        } else {
            call.respondText(
                text = "No birthdays found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostBirthday(birthdayRepository: BirthdayRepository) {
    post {
        val birthday = call.receive<Birthday>()
        birthdayRepository.addBirthday(birthday)
        call.respondText(
            text = "Birthday added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteBirthday(birthdayRepository: BirthdayRepository) {
    delete("{birthdayId}") {
        val birthdayId = call.parameters["birthdayId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing birthdayId",
                status = HttpStatusCode.NotFound
            )
        try {
            birthdayRepository.deleteBirthday(birthdayId)
            call.respondText(
                text = "Birthday deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Birthday with birthdayId = $birthdayId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetBirthday(birthdayRepository: BirthdayRepository) {
    get("{birthdayId}") {
        val birthdayId = call.parameters["birthdayId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing birthdayId",
                status = HttpStatusCode.NotFound
            )
        try {
            val birthday = birthdayRepository.getBirthday(birthdayId)
            call.respond(birthday)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Birthday with birthdayId = $birthdayId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
