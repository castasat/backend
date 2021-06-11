package backend.data.database.tables.service

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object OrdersTable : LongIdTable() {
    private const val OFFER_ID = "OFFER_ID"
    private const val CLIENT_USER_ID = "CLIENT_USER_ID"

    val offerId: Column<Long> = long(name = OFFER_ID)
    val clientUserId: Column<Long> = long(name = CLIENT_USER_ID)
}