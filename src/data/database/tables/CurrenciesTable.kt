package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object CurrenciesTable : LongIdTable() {
    private const val CURRENCY = "CURRENCY"
    val currency: Column<String> =
        varchar(
            name = CURRENCY,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}