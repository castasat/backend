package backend.data.api.routes

import backend.data.api.models.EntranceCode
import backend.data.repositories.EntranceCodeRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.entranceCodeRoute() {
    val entranceCodeRepository: EntranceCodeRepository by di().instance()
    route("/entrance_code") {
        handleGetAllEntranceCodes(entranceCodeRepository = entranceCodeRepository)
        handlePostEntranceCode(entranceCodeRepository = entranceCodeRepository)
        handleDeleteEntranceCode(entranceCodeRepository = entranceCodeRepository)
        handleGetEntranceCode(entranceCodeRepository = entranceCodeRepository)
    }
}

private fun Route.handleGetAllEntranceCodes(entranceCodeRepository: EntranceCodeRepository) {
    get {
        val allEntranceCodes = entranceCodeRepository.getAllEntranceCodes()
        if (allEntranceCodes.isNotEmpty()) {
            call.respond(allEntranceCodes)
        } else {
            call.respondText(
                text = "No entranceCodes found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostEntranceCode(entranceCodeRepository: EntranceCodeRepository) {
    post {
        val entranceCode = call.receive<EntranceCode>()
        entranceCodeRepository.addEntranceCode(entranceCode)
        call.respondText(
            text = "EntranceCode added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteEntranceCode(entranceCodeRepository: EntranceCodeRepository) {
    delete("{entranceCodeId}") {
        val entranceCodeId = call.parameters["entranceCodeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing entranceCodeId",
                status = HttpStatusCode.NotFound
            )
        try {
            entranceCodeRepository.deleteEntranceCode(entranceCodeId)
            call.respondText(
                text = "EntranceCode deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "EntranceCode with entranceCodeId = $entranceCodeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetEntranceCode(entranceCodeRepository: EntranceCodeRepository) {
    get("{entranceCodeId}") {
        val entranceCodeId = call.parameters["entranceCodeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing entranceCodeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val entranceCode = entranceCodeRepository.getEntranceCode(entranceCodeId)
            call.respond(entranceCode)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "EntranceCode with entranceCodeId = $entranceCodeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
