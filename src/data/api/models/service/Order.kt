package backend.data.api.models.service

import backend.data.api.models.user.User
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class Order(
    val orderId: Long,
    val offer: Offer,
    val clientUser: User
)