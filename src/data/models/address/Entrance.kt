package backend.data.models.address

import backend.data.CommonConstants
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// data class for REST API
@Serializable
data class Entrance(
    val entranceId: Long,
    val entrance: String
)

// JDBC Expose Table with autoincrement long primary key
object EntrancesTable : LongIdTable() {
    private const val ENTRANCE = "ENTRANCE"
    val entrance: Column<String> =
        varchar(
            name = ENTRANCE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class EntranceEntity(private var entranceId: EntityID<Long>) : LongEntity(id = entranceId) {
    companion object : LongEntityClass<EntranceEntity>(EntrancesTable)

    @Suppress("MemberVisibilityCanBePrivate")
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