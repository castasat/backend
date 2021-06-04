package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object BuildingsTable : LongIdTable() {
    private const val BUILDING = "BUILDING"
    val building: Column<String> =
        varchar(
            name = BUILDING,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}