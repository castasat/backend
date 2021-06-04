package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class LocalityType(
    val localityTypeId: Long,
    val localityType: String
)