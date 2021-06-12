package backend.data.api.routes.user

import backend.data.api.models.user.Weight
import backend.data.repositories.user.WeightRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di


fun Routing.weightRoute() {
    val weightRepository: WeightRepository by di().instance()
    route("/weight") {
        handleGetAllWeights(weightRepository = weightRepository)
        handlePostWeight(weightRepository = weightRepository)
        handleDeleteWeight(weightRepository = weightRepository)
        handleGetWeight(weightRepository = weightRepository)
    }
}

private fun Route.handleGetAllWeights(weightRepository: WeightRepository) {
    get {
        val allWeights = weightRepository.getAllWeights()
        if (allWeights.isNotEmpty()) {
            call.respond(allWeights)
        } else {
            call.respondText(
                text = "No weights found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostWeight(weightRepository: WeightRepository) {
    post {
        val weight = call.receive<Weight>()
        weightRepository.addWeight(weight)
        call.respondText(
            text = "Weight added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteWeight(weightRepository: WeightRepository) {
    delete("{weightId}") {
        val weightId = call.parameters["weightId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing weightId",
                status = HttpStatusCode.NotFound
            )
        try {
            weightRepository.deleteWeight(weightId)
            call.respondText(
                text = "Weight deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Weight with weightId = $weightId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetWeight(weightRepository: WeightRepository) {
    get("{weightId}") {
        val weightId = call.parameters["weightId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing weightId",
                status = HttpStatusCode.NotFound
            )
        try {
            val weight = weightRepository.getWeight(weightId)
            call.respond(weight)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Weight with weightId = $weightId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
