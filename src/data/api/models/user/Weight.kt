package backend.data.api.models.user

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Weight(
    val weightId: Long,
    val weight: String
)