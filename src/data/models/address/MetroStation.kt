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
data class MetroStation(
    val metroStationId: Long,
    val metroStation: String
)

// JDBC Expose Table with autoincrement long primary key
object MetroStationsTable : LongIdTable() {
    private const val METRO_STATION = "METRO_STATION"
    val metroStation: Column<String> =
        varchar(
            name = METRO_STATION,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class MetroStationEntity(private var metroStationId: EntityID<Long>) : LongEntity(id = metroStationId) {
    companion object : LongEntityClass<MetroStationEntity>(MetroStationsTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var metroStation: String by MetroStationsTable.metroStation

    override fun toString(): String =
        "MetroStation(" +
                "metroStation = $metroStation" +
                ")"

    fun toMetroStation() =
        MetroStation(
            metroStationId = metroStationId.value,
            metroStation = metroStation
        )
}
