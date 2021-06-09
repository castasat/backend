package backend.data.database.tables.price

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PricesTable : LongIdTable() {
    private const val CURRENCY_ID = "CURRENCY_ID"
    private const val PRICE = "PRICE"

    val currencyId: Column<Long> = long(
        name = CURRENCY_ID
    )
    val price: Column<String> = varchar(
        name = PRICE,
        length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
    )
}