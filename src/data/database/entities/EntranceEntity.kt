package backend.data.database.entities

import backend.data.api.models.Entrance
import backend.data.database.tables.EntrancesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class EntranceEntity(private var entranceId: EntityID<Long>) : LongEntity(id = entranceId) {
    companion object : LongEntityClass<EntranceEntity>(EntrancesTable)
    var entrance: String by EntrancesTable.entrance

    override fun toString(): String =
        "Entrance(" +
                "entrance = $entrance" +
                ")"

    fun toEntrance() =
        Entrance(
            entranceId = entranceId.value,
            entrance = entrance
        )
}