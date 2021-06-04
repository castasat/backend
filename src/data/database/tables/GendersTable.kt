package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object GendersTable : LongIdTable() {
    private const val GENDER = "GENDER"
    val gender: Column<String> =
        varchar(
            name = GENDER,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}