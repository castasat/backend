package backend.data.api.models.user

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Gender(
    val genderId: Long,
    val gender: String
)