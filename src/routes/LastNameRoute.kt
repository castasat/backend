package backend.routes

import backend.data.models.LastName
import backend.services.LastNameService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.lastNameRoute() {
    val lastNameService: LastNameService by di().instance()
    route("/last_name") {
        handleGetAllLastNames(lastNameService = lastNameService)
        handlePostLastName(lastNameService = lastNameService)
        handleDeleteLastName(lastNameService = lastNameService)
        handleGetLastName(lastNameService = lastNameService)
    }
}

private fun Route.handleGetAllLastNames(lastNameService: LastNameService) {
    get {
        val allLastNames = lastNameService.getAllLastNames()
        if (allLastNames.isNotEmpty()) {
            call.respond(allLastNames)
        } else {
            call.respondText(
                text = "No last names found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostLastName(lastNameService: LastNameService) {
    post {
        val lastName = call.receive<LastName>()
        lastNameService.addLastName(lastName)
        call.respondText(
            text = "Last name added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLastName(lastNameService: LastNameService) {
    delete("{lastNameId}") {
        val lastNameId = call.parameters["lastNameId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing lastNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            lastNameService.deleteLastName(lastNameId)
            call.respondText(
                text = "Last name deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Last name with lastNameId = $lastNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetLastName(lastNameService: LastNameService) {
    get("{lastNameId}") {
        val lastNameId = call.parameters["lastNameId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing lastNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            val lastName = lastNameService.getLastName(lastNameId)
            call.respond(lastName)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "LastName with lastNameId = $lastNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
