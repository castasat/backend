package backend.routes.user

import backend.data.models.user.Patronymic
import backend.services.user.PatronymicService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.patronymicRoute() {
    val patronymicService: PatronymicService by di().instance()
    route("/patronymic") {
        handleGetAllPatronymics(patronymicService = patronymicService)
        handlePostPatronymic(patronymicService = patronymicService)
        handleDeletePatronymic(patronymicService = patronymicService)
        handleGetPatronymic(patronymicService = patronymicService)
    }
}

private fun Route.handleGetAllPatronymics(patronymicService: PatronymicService) {
    get {
        val allPatronymics = patronymicService.getAllPatronymics()
        if (allPatronymics.isNotEmpty()) {
            call.respond(allPatronymics)
        } else {
            call.respondText(
                text = "No patronymics found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostPatronymic(patronymicService: PatronymicService) {
    post {
        val patronymic = call.receive<Patronymic>()
        patronymicService.addPatronymic(patronymic)
        call.respondText(
            text = "Patronymic added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeletePatronymic(patronymicService: PatronymicService) {
    delete("{patronymicId}") {
        val patronymicId = call.parameters["patronymicId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing patronymicId",
                status = HttpStatusCode.NotFound
            )
        try {
            patronymicService.deletePatronymic(patronymicId)
            call.respondText(
                text = "Patronymic deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Patronymic with patronymicId = $patronymicId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetPatronymic(patronymicService: PatronymicService) {
    get("{patronymicId}") {
        val patronymicId = call.parameters["patronymicId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing patronymicId",
                status = HttpStatusCode.NotFound
            )
        try {
            val patronymic = patronymicService.getPatronymic(patronymicId)
            call.respond(patronymic)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Patronymic with patronymicId = $patronymicId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
