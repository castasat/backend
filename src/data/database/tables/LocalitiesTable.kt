package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object LocalitiesTable : LongIdTable() {
    private const val LOCALITY = "LOCALITY"
    val locality: Column<String> =
        varchar(
            name = LOCALITY,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}