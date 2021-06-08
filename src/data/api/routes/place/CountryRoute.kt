package backend.data.api.routes.place

import backend.data.api.models.place.Country
import backend.data.repositories.place.CountryRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.countryRoute() {
    val countryRepository: CountryRepository by di().instance()
    route("/country") {
        handleGetAllCountries(countryRepository = countryRepository)
        handlePostCountry(countryRepository = countryRepository)
        handleDeleteCountry(countryRepository = countryRepository)
        handleGetCountry(countryRepository = countryRepository)
    }
}

private fun Route.handleGetAllCountries(countryRepository: CountryRepository) {
    get {
        val allCountries = countryRepository.getAllCountries()
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

private fun Route.handlePostCountry(countryRepository: CountryRepository) {
    post {
        val country = call.receive<Country>()
        countryRepository.addCountry(country)
        call.respondText(
            text = "Country added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteCountry(countryRepository: CountryRepository) {
    delete("{countryId}") {
        val countryId = call.parameters["countryId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing countryId",
                status = HttpStatusCode.NotFound
            )
        try {
            countryRepository.deleteCountry(countryId)
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

private fun Route.handleGetCountry(countryRepository: CountryRepository) {
    get("{countryId}") {
        val countryId = call.parameters["countryId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing countryId",
                status = HttpStatusCode.NotFound
            )
        try {
            val country = countryRepository.getCountry(countryId)
            call.respond(country)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Country with countryId = $countryId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}