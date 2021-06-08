package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Building(
    val buildingId: Long,
    val building: String
)