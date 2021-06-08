package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PaymentTypesTable : LongIdTable() {
    private const val PAYMENT_TYPE = "PAYMENT_TYPE"
    val paymentType: Column<String> =
        varchar(
            name = PAYMENT_TYPE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}