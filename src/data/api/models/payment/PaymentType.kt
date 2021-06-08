package backend.data.api.models.payment

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class PaymentType(
    val paymentTypeId: Long,
    val paymentType: String
)