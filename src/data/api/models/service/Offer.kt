package backend.data.api.models.service

import backend.data.api.models.place.Address
import backend.data.api.models.price.Price
import backend.data.api.models.user.User
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class Offer(
    val offerId: Long,
    val service: Service,
    val price: Price,
    val address: Address,
    val specialistUser: User
)