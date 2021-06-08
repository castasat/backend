package backend.data.api.models.user

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Patronymic(
    val patronymicId: Long,
    val patronymic: String
)