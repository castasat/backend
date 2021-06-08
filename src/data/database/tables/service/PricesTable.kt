package backend.data.database.tables.service

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PricesTable : LongIdTable() {
    private const val PRICE = "PRICE"
    val price: Column<String> =
        varchar(
            name = PRICE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}