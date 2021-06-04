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
data class Coordinates(
    val coordinatesId: Long,
    val longitude: String,
    val latitude: String
)

// JDBC Expose Table with autoincrement long primary key
object CoordinatesTable : LongIdTable() {
    private const val LONGITUDE = "LONGITUDE"
    private const val LATITUDE = "LATITUDE"
    val longitude: Column<String> =
        varchar(
            name = LONGITUDE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
    val latitude: Column<String> =
        varchar(
            name = LATITUDE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class CoordinatesEntity(private var coordinatesId: EntityID<Long>) : LongEntity(id = coordinatesId) {
    companion object : LongEntityClass<CoordinatesEntity>(CoordinatesTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var longitude: String by CoordinatesTable.longitude

    @Suppress("MemberVisibilityCanBePrivate")
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