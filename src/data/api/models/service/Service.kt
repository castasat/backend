package backend.data.api.models.service

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Service(
    val serviceId: Long,
    val serviceType: ServiceType,
    val duration: String
)