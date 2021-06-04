package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Street(
    val streetId: Long,
    val street: String
)