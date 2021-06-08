package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Entrance(
    val entranceId: Long,
    val entrance: String
)