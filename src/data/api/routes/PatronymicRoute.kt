package backend.data.api.routes

import backend.data.api.models.Patronymic
import backend.data.repositories.PatronymicRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.patronymicRoute() {
    val patronymicRepository: PatronymicRepository by di().instance()
    route("/patronymic") {
        handleGetAllPatronymics(patronymicRepository = patronymicRepository)
        handlePostPatronymic(patronymicRepository = patronymicRepository)
        handleDeletePatronymic(patronymicRepository = patronymicRepository)
        handleGetPatronymic(patronymicRepository = patronymicRepository)
    }
}

private fun Route.handleGetAllPatronymics(patronymicRepository: PatronymicRepository) {
    get {
        val allPatronymics = patronymicRepository.getAllPatronymics()
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

private fun Route.handlePostPatronymic(patronymicRepository: PatronymicRepository) {
    post {
        val patronymic = call.receive<Patronymic>()
        patronymicRepository.addPatronymic(patronymic)
        call.respondText(
            text = "Patronymic added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeletePatronymic(patronymicRepository: PatronymicRepository) {
    delete("{patronymicId}") {
        val patronymicId = call.parameters["patronymicId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing patronymicId",
                status = HttpStatusCode.NotFound
            )
        try {
            patronymicRepository.deletePatronymic(patronymicId)
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

private fun Route.handleGetPatronymic(patronymicRepository: PatronymicRepository) {
    get("{patronymicId}") {
        val patronymicId = call.parameters["patronymicId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing patronymicId",
                status = HttpStatusCode.NotFound
            )
        try {
            val patronymic = patronymicRepository.getPatronymic(patronymicId)
            call.respond(patronymic)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Patronymic with patronymicId = $patronymicId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
