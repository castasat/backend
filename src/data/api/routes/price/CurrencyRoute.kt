package backend.data.api.routes.price

import backend.data.api.models.price.Currency
import backend.data.repositories.price.CurrencyRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.currencyRoute() {
    val currencyRepository: CurrencyRepository by di().instance()
    route("/currency") {
        handleGetAllCurrencies(currencyRepository = currencyRepository)
        handlePostCurrency(currencyRepository = currencyRepository)
        handleDeleteCurrency(currencyRepository = currencyRepository)
        handleGetCurrency(currencyRepository = currencyRepository)
    }
}

private fun Route.handleGetAllCurrencies(currencyRepository: CurrencyRepository) {
    get {
        val allCurrencies = currencyRepository.getAllCurrencies()
        if (allCurrencies.isNotEmpty()) {
            call.respond(allCurrencies)
        } else {
            call.respondText(
                text = "No currencies found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostCurrency(currencyRepository: CurrencyRepository) {
    post {
        val currency = call.receive<Currency>()
        currencyRepository.addCurrency(currency)
        call.respondText(
            text = "Currency added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteCurrency(currencyRepository: CurrencyRepository) {
    delete("{currencyId}") {
        val currencyId = call.parameters["currencyId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing currencyId",
                status = HttpStatusCode.NotFound
            )
        try {
            currencyRepository.deleteCurrency(currencyId)
            call.respondText(
                text = "Currency deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Currency with currencyId = $currencyId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetCurrency(currencyRepository: CurrencyRepository) {
    get("{currencyId}") {
        val currencyId = call.parameters["currencyId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing currencyId",
                status = HttpStatusCode.NotFound
            )
        try {
            val currency = currencyRepository.getCurrency(currencyId)
            call.respond(currency)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Currency with currencyId = $currencyId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}