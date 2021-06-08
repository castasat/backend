package backend.data.api.routes.place

import backend.data.api.models.place.Address
import backend.data.repositories.place.AddressRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

fun Routing.addressRoute() {
    val addressRepository: AddressRepository by di().instance()
    route("/address") {
        handleGetAllAddresses(addressRepository = addressRepository)
        handlePostAddress(addressRepository = addressRepository)
        handleDeleteAddress(addressRepository = addressRepository)
        handleGetAddress(addressRepository = addressRepository)
    }
}

private fun Route.handleGetAllAddresses(addressRepository: AddressRepository) {
    get {
        val allAddresses = addressRepository.getAllAddresses()
        if (allAddresses.isNotEmpty()) {
            call.respond(allAddresses)
        } else {
            call.respondText(
                text = "No addresses found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handlePostAddress(addressRepository: AddressRepository) {
    post {
        val address = call.receive<Address>()
        addressRepository.addAddress(address)
        call.respondText(
            text = "Address added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteAddress(addressRepository: AddressRepository) {
    delete("{addressId}") {
        val addressId = call.parameters["addressId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing addressId",
                status = HttpStatusCode.NotFound
            )
        try {
            addressRepository.deleteAddress(addressId)
            call.respondText(
                text = "Address deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "Address with addressId = $addressId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

private fun Route.handleGetAddress(addressRepository: AddressRepository) {
    get("{addressId}") {
        val addressId = call.parameters["addressId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing addressId",
                status = HttpStatusCode.NotFound
            )
        try {
            val address = addressRepository.getAddress(addressId)
            call.respond(address)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "Address with addressId = $addressId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
