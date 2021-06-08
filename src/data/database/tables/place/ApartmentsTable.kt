package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object ApartmentsTable : LongIdTable() {
    private const val APARTMENT = "APARTMENT"
    val apartment: Column<String> =
        varchar(
            name = APARTMENT,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}