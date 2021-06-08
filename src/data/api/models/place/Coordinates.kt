package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Coordinates(
    val coordinatesId: Long,
    val longitude: String,
    val latitude: String
)