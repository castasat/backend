package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object CountriesTable : LongIdTable() {
    private const val COUNTRY = "COUNTRY"
    val country: Column<String> =
        varchar(
            name = COUNTRY,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}