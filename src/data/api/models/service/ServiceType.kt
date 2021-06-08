package backend.data.api.models.service

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class ServiceType(
    val serviceTypeId: Long,
    val serviceType: String
)