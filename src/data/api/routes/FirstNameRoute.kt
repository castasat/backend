package backend.data.api.routes

import backend.data.api.models.FirstName
import backend.data.repositories.FirstNameRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.firstNameRoute() {
    val firstNameRepository: FirstNameRepository by di().instance()
    route("/first_name") {
        handleGetAllFirstNames(firstNameRepository = firstNameRepository)
        handlePostFirstName(firstNameRepository = firstNameRepository)
        handleDeleteFirstName(firstNameRepository = firstNameRepository)
        handleGetFirstName(firstNameRepository = firstNameRepository)
    }
}

private fun Route.handleGetAllFirstNames(firstNameRepository: FirstNameRepository) {
    get {
        val allFirstNames = firstNameRepository.getAllFirstNames()
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

private fun Route.handlePostFirstName(firstNameRepository: FirstNameRepository) {
    post {
        val firstName = call.receive<FirstName>()
        firstNameRepository.addFirstName(firstName)
        call.respondText(
            text = "First name added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteFirstName(firstNameRepository: FirstNameRepository) {
    delete("{firstNameId}") {
        val firstNameId = call.parameters["firstNameId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing firstNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            firstNameRepository.deleteFirstName(firstNameId)
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

private fun Route.handleGetFirstName(firstNameRepository: FirstNameRepository) {
    get("{firstNameId}") {
        val firstNameId = call.parameters["firstNameId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing firstNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            val firstName = firstNameRepository.getFirstName(firstNameId)
            call.respond(firstName)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "FirstName with firstNameId = $firstNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}