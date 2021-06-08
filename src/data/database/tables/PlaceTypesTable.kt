package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PlaceTypesTable : LongIdTable() {
    private const val PLACE_TYPE = "PLACE_TYPE"
    val placeType: Column<String> =
        varchar(
            name = PLACE_TYPE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}