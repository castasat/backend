package backend.data.database.tables.search

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object OfferSearchesTable : LongIdTable() {
    private const val CLIENT_USER_ID = "CLIENT_USER_ID"
    private const val SERVICE_TYPE_ID = "SERVICE_TYPE_ID"
    private const val PLACE_TYPE_ID = "PLACE_TYPE_ID"
    private const val MIN_PRICE_ID = "MIN_PRICE_ID"
    private const val MAX_PRICE_ID = "MAX_PRICE_ID"
    private const val PAYMENT_TYPE_ID = "PAYMENT_TYPE_ID"
    private const val SPECIALIST_GENDER_ID = "SPECIALIST_GENDER_ID"

    val clientUserId: Column<Long> = long(name = CLIENT_USER_ID)
    val serviceTypeId: Column<Long> = long(name = SERVICE_TYPE_ID)
    val placeTypeId: Column<Long> = long(name = PLACE_TYPE_ID)
    val minPriceId: Column<Long> = long(name = MIN_PRICE_ID)
    val maxPriceId: Column<Long> = long(name = MAX_PRICE_ID)
    val paymentTypeId: Column<Long> = long(name = PAYMENT_TYPE_ID)
    val specialistGenderId: Column<Long> = long(name = SPECIALIST_GENDER_ID)
}