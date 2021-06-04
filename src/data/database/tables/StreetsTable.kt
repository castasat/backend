package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object StreetsTable : LongIdTable() {
    private const val STREET = "STREET"
    val street: Column<String> =
        varchar(
            name = STREET,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}