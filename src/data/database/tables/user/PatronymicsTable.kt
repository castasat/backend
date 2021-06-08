package backend.data.database.tables.user

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PatronymicsTable : LongIdTable() {
    private const val PATRONYMIC = "PATRONYMIC"
    val patronymic: Column<String> =
        varchar(
            name = PATRONYMIC,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}