package backend.data.database.tables.user

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object FirstNamesTable : LongIdTable() {
    private const val FIRST_NAME = "FIRST_NAME"
    val firstName: Column<String> =
        varchar(
            name = FIRST_NAME,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}