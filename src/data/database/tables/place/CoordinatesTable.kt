package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

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