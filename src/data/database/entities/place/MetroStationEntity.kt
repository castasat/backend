package backend.data.database.entities.place

import backend.data.api.models.place.MetroStation
import backend.data.database.tables.place.MetroStationsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class MetroStationEntity(private var metroStationId: EntityID<Long>) : LongEntity(id = metroStationId) {
    companion object : LongEntityClass<MetroStationEntity>(MetroStationsTable)
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