package backend.data.api.models.search

import backend.data.api.models.place.PlaceType
import backend.data.api.models.price.Price
import backend.data.api.models.service.ServiceType
import backend.data.api.models.user.Gender
import backend.data.api.models.user.User
import backend.data.api.models.user.Weight
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class ClientSearch(
    val clientSearchId: Long,
    val specialistUser: User,
    val serviceType: ServiceType,
    val placeType: PlaceType,
    val minPrice: Price,
    val maxPrice: Price,
    val clientGender: Gender,
    val maxClientWeight: Weight,
    val minClientAge: Int,
    val maxClientAge: Int
)