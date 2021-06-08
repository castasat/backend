package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object MetroStationsTable : LongIdTable() {
    private const val METRO_STATION = "METRO_STATION"
    val metroStation: Column<String> =
        varchar(
            name = METRO_STATION,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}