package backend.data.api.routes.user

import backend.data.api.models.user.LastName
import backend.data.repositories.user.LastNameRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.lastNameRoute() {
    val lastNameRepository: LastNameRepository by di().instance()
    route("/last_name") {
        handleGetAllLastNames(lastNameRepository = lastNameRepository)
        handlePostLastName(lastNameRepository = lastNameRepository)
        handleDeleteLastName(lastNameRepository = lastNameRepository)
        handleGetLastName(lastNameRepository = lastNameRepository)
    }
}

private fun Route.handleGetAllLastNames(lastNameRepository: LastNameRepository) {
    get {
        val allLastNames = lastNameRepository.getAllLastNames()
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

private fun Route.handlePostLastName(lastNameRepository: LastNameRepository) {
    post {
        val lastName = call.receive<LastName>()
        lastNameRepository.addLastName(lastName)
        call.respondText(
            text = "Last name added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteLastName(lastNameRepository: LastNameRepository) {
    delete("{lastNameId}") {
        val lastNameId = call.parameters["lastNameId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing lastNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            lastNameRepository.deleteLastName(lastNameId)
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

private fun Route.handleGetLastName(lastNameRepository: LastNameRepository) {
    get("{lastNameId}") {
        val lastNameId = call.parameters["lastNameId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing lastNameId",
                status = HttpStatusCode.NotFound
            )
        try {
            val lastName = lastNameRepository.getLastName(lastNameId)
            call.respond(lastName)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "LastName with lastNameId = $lastNameId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
