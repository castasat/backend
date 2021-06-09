package backend.data.database.tables.service

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object OffersTable : LongIdTable() {
    private const val SERVICE_ID = "SERVICE_ID"
    private const val PRICE_ID = "PRICE_ID"
    private const val ADDRESS_ID = "ADDRESS_ID"
    private const val SPECIALIST_USER_ID = "SPECIALIST_USER_ID"

    val serviceId: Column<Long> = long(name = SERVICE_ID)
    val priceId: Column<Long> = long(name = PRICE_ID)
    val addressId: Column<Long> = long(name = ADDRESS_ID)
    val specialistUserId: Column<Long> = long(name = SPECIALIST_USER_ID)
}