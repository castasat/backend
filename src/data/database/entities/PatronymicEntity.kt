package backend.data.database.entities

import backend.data.api.models.Patronymic
import backend.data.database.tables.PatronymicsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class PatronymicEntity(private var patronymicId: EntityID<Long>) : LongEntity(id = patronymicId) {
    companion object : LongEntityClass<PatronymicEntity>(PatronymicsTable)
    var patronymic: String by PatronymicsTable.patronymic

    override fun toString(): String =
        "Patronymic(" +
                "patronymic = $patronymic" +
                ")"

    fun toPatronymic() =
        Patronymic(
            patronymicId = patronymicId.value,
            patronymic = patronymic
        )
}