package backend.data.database.entities.place

import backend.data.api.models.place.Coordinates
import backend.data.database.tables.place.CoordinatesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class CoordinatesEntity(private var coordinatesId: EntityID<Long>) : LongEntity(id = coordinatesId) {
    companion object : LongEntityClass<CoordinatesEntity>(CoordinatesTable)
    var longitude: String by CoordinatesTable.longitude
    var latitude: String by CoordinatesTable.latitude

    override fun toString(): String =
        "Coordinates(" +
                "longitude = $longitude" +
                "latitude = $latitude" +
                ")"

    fun toCoordinates() =
        Coordinates(
            coordinatesId = coordinatesId.value,
            longitude = longitude,
            latitude = latitude
        )
}