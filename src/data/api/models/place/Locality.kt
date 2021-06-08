package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Locality(
    val localityId: Long,
    val locality: String
)