package backend.data.api.models.price

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Price(
    val priceId: Long,
    val currency: Currency,
    val price: String
)