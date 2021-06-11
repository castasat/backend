package backend.data.database.tables.payment

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object PaymentsTable : LongIdTable() {
    private const val ORDER_ID = "ORDER_ID"
    private const val PAYMENT_TYPE_ID = "PAYMENT_TYPE_ID"
    private const val PAYMENT_AMOUNT = "PAYMENT_AMOUNT"

    val orderId: Column<Long> = long(name = ORDER_ID)
    val paymentTypeId: Column<Long> = long(name = PAYMENT_TYPE_ID)
    val paymentAmount: Column<String> = varchar(
        name = PAYMENT_AMOUNT,
        length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
    )
}