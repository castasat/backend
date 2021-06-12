package backend.data.api.routes.search

import backend.data.api.models.search.ClientSearch
import backend.data.repositories.search.ClientSearchRepository
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
fun Routing.clientSearchRoute() {
    val clientSearchRepository: ClientSearchRepository by di().instance()
    route("/client_search") {
        handleGetAllClientSearches(clientSearchRepository = clientSearchRepository)
        handlePostClientSearch(clientSearchRepository = clientSearchRepository)
        handleDeleteClientSearch(clientSearchRepository = clientSearchRepository)
        handleGetClientSearch(clientSearchRepository = clientSearchRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllClientSearches(clientSearchRepository: ClientSearchRepository) {
    get {
        val allClientSearches = clientSearchRepository.getAllClientSearches()
        if (allClientSearches.isNotEmpty()) {
            call.respond(allClientSearches)
        } else {
            call.respondText(
                text = "No clientSearches found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostClientSearch(clientSearchRepository: ClientSearchRepository) {
    post {
        val clientSearch = call.receive<ClientSearch>()
        clientSearchRepository.addClientSearch(clientSearch)
        call.respondText(
            text = "ClientSearch added",
            status = HttpStatusCode.Created
        )
    }
}

@ExperimentalSerializationApi
private fun Route.handleDeleteClientSearch(clientSearchRepository: ClientSearchRepository) {
    delete("{clientSearchId}") {
        val clientSearchId = call.parameters["clientSearchId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing clientSearchId",
                status = HttpStatusCode.NotFound
            )
        try {
            clientSearchRepository.deleteClientSearch(clientSearchId)
            call.respondText(
                text = "ClientSearch deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "ClientSearch with clientSearchId = $clientSearchId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetClientSearch(clientSearchRepository: ClientSearchRepository) {
    get("{clientSearchId}") {
        val clientSearchId = call.parameters["clientSearchId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing clientSearchId",
                status = HttpStatusCode.NotFound
            )
        try {
            val clientSearch = clientSearchRepository.getClientSearch(clientSearchId)
            call.respond(clientSearch)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "ClientSearch with clientSearchId = $clientSearchId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}