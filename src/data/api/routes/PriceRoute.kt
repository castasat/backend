package backend.data.api.routes

import backend.data.api.models.Price
import backend.data.repositories.PriceRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.priceRoute() {
    val priceRepository: PriceRepository by di().instance()
    route("/price") {
        handleGetAllPrices(priceRepository = priceRepository)
        handlePostPrice(priceRepository = priceRepository)
        handleDeletePrice(priceRepository = priceRepository)
        handleGetPrice(priceRepository = priceRepository)
    }
}

private fun Route.handleGetAllPrices(priceRepository: PriceRepository) {
    get {
        val allPrices = priceRepository.getAllPrices()
        if (allPrices.isNotEmpty()) {
            call.respond(allPrices)
        } else {
            call.respondText(
                text = "No prices found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostPrice(priceRepository: PriceRepository) {
    post {
        val price = call.receive<Price>()
        priceRepository.addPrice(price)
        call.respondText(
            text = "Price added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeletePrice(priceRepository: PriceRepository) {
    delete("{priceId}") {
        val priceId = call.parameters["priceId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing priceId",
                status = HttpStatusCode.NotFound
            )
        try {
            priceRepository.deletePrice(priceId)
            call.respondText(
                text = "Price deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Price with priceId = $priceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetPrice(priceRepository: PriceRepository) {
    get("{priceId}") {
        val priceId = call.parameters["priceId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing priceId",
                status = HttpStatusCode.NotFound
            )
        try {
            val price = priceRepository.getPrice(priceId)
            call.respond(price)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Price with priceId = $priceId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
