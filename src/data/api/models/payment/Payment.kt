package backend.data.api.models.payment

import backend.data.api.models.service.Order
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class Payment(
    val paymentId: Long,
    val order: Order,
    val paymentType: PaymentType,
    val paymentAmount: String
)