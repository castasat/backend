package backend.routes.address

import backend.data.models.address.EntranceCode
import backend.services.address.EntranceCodeService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.entranceCodeRoute() {
    val entranceCodeService: EntranceCodeService by di().instance()
    route("/entrance_code") {
        handleGetAllEntranceCodes(entranceCodeService = entranceCodeService)
        handlePostEntranceCode(entranceCodeService = entranceCodeService)
        handleDeleteEntranceCode(entranceCodeService = entranceCodeService)
        handleGetEntranceCode(entranceCodeService = entranceCodeService)
    }
}

private fun Route.handleGetAllEntranceCodes(entranceCodeService: EntranceCodeService) {
    get {
        val allEntranceCodes = entranceCodeService.getAllEntranceCodes()
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

private fun Route.handlePostEntranceCode(entranceCodeService: EntranceCodeService) {
    post {
        val entranceCode = call.receive<EntranceCode>()
        entranceCodeService.addEntranceCode(entranceCode)
        call.respondText(
            text = "EntranceCode added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteEntranceCode(entranceCodeService: EntranceCodeService) {
    delete("{entranceCodeId}") {
        val entranceCodeId = call.parameters["entranceCodeId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing entranceCodeId",
                status = HttpStatusCode.NotFound
            )
        try {
            entranceCodeService.deleteEntranceCode(entranceCodeId)
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

private fun Route.handleGetEntranceCode(entranceCodeService: EntranceCodeService) {
    get("{entranceCodeId}") {
        val entranceCodeId = call.parameters["entranceCodeId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing entranceCodeId",
                status = HttpStatusCode.NotFound
            )
        try {
            val entranceCode = entranceCodeService.getEntranceCode(entranceCodeId)
            call.respond(entranceCode)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "EntranceCode with entranceCodeId = $entranceCodeId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
