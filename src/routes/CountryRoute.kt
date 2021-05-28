package backend.routes

import backend.data.models.Country
import backend.services.CountryService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.countryRoute() {
    val countryService: CountryService by di().instance()
    route("/country") {
        handleGetAllCountries(countryService = countryService)
        handlePostCountry(countryService = countryService)
        handleDeleteCountry(countryService = countryService)
        handleGetCountry(countryService = countryService)
    }
}

private fun Route.handleGetAllCountries(countryService: CountryService) {
    get {
        val allCountries = countryService.getAllCountries()
        if (allCountries.isNotEmpty()) {
            call.respond(allCountries)
        } else {
            call.respondText(
                text = "No countries found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostCountry(countryService: CountryService) {
    post {
        val country = call.receive<Country>()
        countryService.addCountry(country)
        call.respondText(
            text = "Country added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteCountry(countryService: CountryService) {
    delete("{countryId}") {
        val countryId = call.parameters["countryId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing countryId",
                status = HttpStatusCode.NotFound
            )
        try {
            countryService.deleteCountry(countryId)
            call.respondText(
                text = "Country deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Country with countryId = $countryId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetCountry(countryService: CountryService) {
    get("{countryId}") {
        val countryId = call.parameters["countryId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing countryId",
                status = HttpStatusCode.NotFound
            )
        try {
            val country = countryService.getCountry(countryId)
            call.respond(country)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Country with countryId = $countryId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}