package backend.data.api.models.user

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class User(
    val userId: Long,
    val firstNameId: Long,
    val lastNameId: Long,
    val patronymic: Long,
    val genderId: Long,
    val birthdayId: Long,
    val addressId: Long
)