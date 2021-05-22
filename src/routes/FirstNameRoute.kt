package backend.routes

import backend.data.models.FirstName
import backend.services.FirstNameService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.firstNameRoute() {
    route("/") {
        get {
            call.respondText(
                text = "Hello, Backend on Kotlin powered by Ktor on Tomcat!",
                status = HttpStatusCode.OK
            )
        }

        val firstNameService: FirstNameService by di().instance()

        route("/first_name") {
            handleGetAllFirstNames(firstNameService = firstNameService)
            handlePostFirstName(firstNameService = firstNameService)
            handleDeleteFirstName(firstNameService = firstNameService)
            handleGetFirstName(firstNameService = firstNameService)
        }
    }
}

private fun Route.handleGetAllFirstNames(firstNameService: FirstNameService) {
    get {
        val allFirstNames = firstNameService.getAllFirstNames()
        if (allFirstNames.isNotEmpty()) {
            call.respond(allFirstNames)
        } else {
            call.respondText(
                text = "No first names found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostFirstName(firstNameService: FirstNameService) {
    post {
        val firstName = call.receive<FirstName>()
        firstNameService.addFirstName(firstName)
        call.respondText(
            text = "First name added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteFirstName(firstNameService: FirstNameService) {
    delete("{firstNameId}") {
        val firstNameId = call.parameters["firstNameId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing firstNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            firstNameService.deleteFirstName(firstNameId)
            call.respondText(
                text = "First name deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "First name with firstNameId = $firstNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetFirstName(firstNameService: FirstNameService) {
    get("{firstNameId}") {
        val firstNameId = call.parameters["firstNameId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing firstNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            val firstName = firstNameService.getFirstName(firstNameId)
            call.respond(firstName)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "FirstName with firstNameId = $firstNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}