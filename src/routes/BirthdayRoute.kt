package backend.routes

import backend.data.models.Birthday
import backend.services.BirthdayService
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
    val birthdayService: BirthdayService by di().instance()
    route("/birthday") {
        handleGetAllBirthdays(birthdayService = birthdayService)
        handlePostBirthday(birthdayService = birthdayService)
        handleDeleteBirthday(birthdayService = birthdayService)
        handleGetBirthday(birthdayService = birthdayService)
    }
}

private fun Route.handleGetAllBirthdays(birthdayService: BirthdayService) {
    get {
        val allBirthdays = birthdayService.getAllBirthdays()
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
private fun Route.handlePostBirthday(birthdayService: BirthdayService) {
    post {
        val birthday = call.receive<Birthday>()
        birthdayService.addBirthday(birthday)
        call.respondText(
            text = "Birthday added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteBirthday(birthdayService: BirthdayService) {
    delete("{birthdayId}") {
        val birthdayId = call.parameters["birthdayId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing birthdayId",
                status = HttpStatusCode.NotFound
            )
        try {
            birthdayService.deleteBirthday(birthdayId)
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

private fun Route.handleGetBirthday(birthdayService: BirthdayService) {
    get("{birthdayId}") {
        val birthdayId = call.parameters["birthdayId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing birthdayId",
                status = HttpStatusCode.NotFound
            )
        try {
            val birthday = birthdayService.getBirthday(birthdayId)
            call.respond(birthday)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Birthday with birthdayId = $birthdayId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
