package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Apartment(
    val apartmentId: Long,
    val apartment: String
)