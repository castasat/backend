package backend.data.api.models.search

import backend.data.api.models.payment.PaymentType
import backend.data.api.models.place.PlaceType
import backend.data.api.models.price.Price
import backend.data.api.models.service.ServiceType
import backend.data.api.models.user.Gender
import backend.data.api.models.user.User
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class OfferSearch(
    val offerSearchId: Long,
    val clientUser: User,
    val serviceType: ServiceType,
    val placeType: PlaceType,
    val minPrice: Price,
    val maxPrice: Price,
    val paymentType: PaymentType,
    val specialistGender: Gender
)