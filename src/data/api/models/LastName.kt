package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class LastName(
    val lastNameId: Long,
    val lastName: String
)