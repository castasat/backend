package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Country(
    val countryId: Long,
    val country: String
)