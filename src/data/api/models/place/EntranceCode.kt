package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class EntranceCode(
    val entranceCodeId: Long,
    val entranceCode: String
)