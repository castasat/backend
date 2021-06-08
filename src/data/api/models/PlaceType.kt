package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class PlaceType(
    val placeTypeId: Long,
    val placeType: String
)