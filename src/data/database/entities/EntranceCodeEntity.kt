package backend.data.database.entities

import backend.data.api.models.EntranceCode
import backend.data.database.tables.EntranceCodesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class EntranceCodeEntity(private var entranceCodeId: EntityID<Long>) : LongEntity(id = entranceCodeId) {
    companion object : LongEntityClass<EntranceCodeEntity>(EntranceCodesTable)
    var entranceCode: String by EntranceCodesTable.entranceCode

    override fun toString(): String =
        "EntranceCode(" +
                "entranceCode = $entranceCode" +
                ")"

    fun toEntranceCode() =
        EntranceCode(
            entranceCodeId = entranceCodeId.value,
            entranceCode = entranceCode
        )
}