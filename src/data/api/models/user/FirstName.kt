package backend.data.api.models.user

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class FirstName(
    val firstNameId: Long,
    val firstName: String
)