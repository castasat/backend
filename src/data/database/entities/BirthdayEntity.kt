package backend.data.database.entities

import backend.data.api.models.Birthday
import backend.data.database.tables.BirthdaysTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDate

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class BirthdayEntity(private var birthdayId: EntityID<Long>) : LongEntity(id = birthdayId) {
    companion object : LongEntityClass<BirthdayEntity>(BirthdaysTable)

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