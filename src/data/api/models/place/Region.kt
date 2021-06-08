package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Region(
    val regionId: Long,
    val region: String
)