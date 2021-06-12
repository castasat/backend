package backend.data.api.routes.search

import backend.data.api.models.search.OfferSearch
import backend.data.repositories.search.OfferSearchRepository
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
fun Routing.offerSearchRoute() {
    val offerSearchRepository: OfferSearchRepository by di().instance()
    route("/offer_search") {
        handleGetAllOfferSearches(offerSearchRepository = offerSearchRepository)
        handlePostOfferSearch(offerSearchRepository = offerSearchRepository)
        handleDeleteOfferSearch(offerSearchRepository = offerSearchRepository)
        handleGetOfferSearch(offerSearchRepository = offerSearchRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllOfferSearches(offerSearchRepository: OfferSearchRepository) {
    get {
        val allOfferSearches = offerSearchRepository.getAllOfferSearches()
        if (allOfferSearches.isNotEmpty()) {
            call.respond(allOfferSearches)
        } else {
            call.respondText(
                text = "No offerSearches found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostOfferSearch(offerSearchRepository: OfferSearchRepository) {
    post {
        val offerSearch = call.receive<OfferSearch>()
        offerSearchRepository.addOfferSearch(offerSearch)
        call.respondText(
            text = "OfferSearch added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteOfferSearch(offerSearchRepository: OfferSearchRepository) {
    delete("{offerSearchId}") {
        val offerSearchId = call.parameters["offerSearchId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing offerSearchId",
                status = HttpStatusCode.NotFound
            )
        try {
            offerSearchRepository.deleteOfferSearch(offerSearchId)
            call.respondText(
                text = "OfferSearch deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "OfferSearch with offerSearchId = $offerSearchId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetOfferSearch(offerSearchRepository: OfferSearchRepository) {
    get("{offerSearchId}") {
        val offerSearchId = call.parameters["offerSearchId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing offerSearchId",
                status = HttpStatusCode.NotFound
            )
        try {
            val offerSearch = offerSearchRepository.getOfferSearch(offerSearchId)
            call.respond(offerSearch)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "OfferSearch with offerSearchId = $offerSearchId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
