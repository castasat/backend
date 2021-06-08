package backend.data.api.models.user

import backend.data.api.serialization.LocalDateSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.time.LocalDate

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class Birthday(
    val birthdayId: Long,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate
)