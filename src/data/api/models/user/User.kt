package backend.data.api.models.user

import backend.data.api.models.place.Address
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class User(
    val userId: Long,
    val firstName: FirstName,
    val lastName: LastName,
    val patronymic: Patronymic,
    val gender: Gender,
    val weight: Weight,
    val birthday: Birthday,
    val address: Address
)