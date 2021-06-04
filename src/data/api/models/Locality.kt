package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Locality(
    val localityId: Long,
    val locality: String
)