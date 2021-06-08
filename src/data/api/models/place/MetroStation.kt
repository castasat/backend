package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class MetroStation(
    val metroStationId: Long,
    val metroStation: String
)