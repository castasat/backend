package backend.data.database.tables.user

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object LastNamesTable : LongIdTable() {
    private const val LAST_NAME = "LAST_NAME"
    val lastName: Column<String> =
        varchar(
            name = LAST_NAME,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}