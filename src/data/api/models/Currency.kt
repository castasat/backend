package backend.data.api.models

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Currency(
    val currencyId: Long,
    val currency: String
)