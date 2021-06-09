package backend.data.api.routes.service

import backend.data.api.models.service.Offer
import backend.data.repositories.service.OfferRepository
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
fun Routing.offerRoute() {
    val offerRepository: OfferRepository by di().instance()
    route("/offer") {
        handleGetAllOffers(offerRepository = offerRepository)
        handlePostOffer(offerRepository = offerRepository)
        handleDeleteOffer(offerRepository = offerRepository)
        handleGetOffer(offerRepository = offerRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllOffers(offerRepository: OfferRepository) {
    get {
        val allOffers = offerRepository.getAllOffers()
        if (allOffers.isNotEmpty()) {
            call.respond(allOffers)
        } else {
            call.respondText(
                text = "No offers found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostOffer(offerRepository: OfferRepository) {
    post {
        val offer = call.receive<Offer>()
        offerRepository.addOffer(offer)
        call.respondText(
            text = "Offer added",
            status = HttpStatusCode.Created
        )
    }
}

@ExperimentalSerializationApi
private fun Route.handleDeleteOffer(offerRepository: OfferRepository) {
    delete("{offerId}") {
        val offerId = call.parameters["offerId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing offerId",
                status = HttpStatusCode.NotFound
            )
        try {
            offerRepository.deleteOffer(offerId)
            call.respondText(
                text = "Offer deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Offer with offerId = $offerId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetOffer(offerRepository: OfferRepository) {
    get("{offerId}") {
        val offerId = call.parameters["offerId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing offerId",
                status = HttpStatusCode.NotFound
            )
        try {
            val offer = offerRepository.getOffer(offerId)
            call.respond(offer)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Offer with offerId = $offerId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
