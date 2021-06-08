package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object EntrancesTable : LongIdTable() {
    private const val ENTRANCE = "ENTRANCE"
    val entrance: Column<String> =
        varchar(
            name = ENTRANCE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}