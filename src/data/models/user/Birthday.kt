package backend.data.models.user

import backend.data.serialization.LocalDateSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

// data class for REST API
@ExperimentalSerializationApi
@Serializable
data class Birthday(
    val birthdayId: Long,
    @Serializable(with = LocalDateSerializer::class)
    val birthday: LocalDate
)

// JDBC Expose Table with autoincrement long primary key
object BirthdaysTable : LongIdTable() {
    private const val BIRTHDAY = "BIRTHDAY"
    val birthday: Column<LocalDate> =
        date(
            name = BIRTHDAY
        )
}

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class BirthdayEntity(private var birthdayId: EntityID<Long>) : LongEntity(id = birthdayId) {
    companion object : LongEntityClass<BirthdayEntity>(BirthdaysTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var birthday: LocalDate by BirthdaysTable.birthday

    override fun toString(): String =
        "Birthday(" +
                "birthday = $birthday" +
                ")"

    fun toBirthday() =
        Birthday(
            birthdayId = birthdayId.value,
            birthday = birthday
        )
}