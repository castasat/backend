package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Country(
    val countryId: Long,
    val country: String
)