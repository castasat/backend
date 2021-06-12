package backend.data.database.tables.search

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object ClientSearchesTable : LongIdTable() {
    private const val SPECIALIST_USER_ID = "SPECIALIST_USER_ID"
    private const val SERVICE_TYPE_ID = "SERVICE_TYPE_ID"
    private const val PLACE_TYPE_ID = "PLACE_TYPE_ID"
    private const val MIN_PRICE_ID = "MIN_PRICE_ID"
    private const val MAX_PRICE_ID = "MAX_PRICE_ID"
    private const val CLIENT_GENDER_ID = "CLIENT_GENDER_ID"
    private const val MAX_CLIENT_WEIGHT_ID = "MAX_CLIENT_WEIGHT_ID"
    private const val MIN_CLIENT_AGE = "MIN_CLIENT_AGE"
    private const val MAX_CLIENT_AGE = "MAX_CLIENT_AGE"

    val specialistUserId: Column<Long> = long(name = SPECIALIST_USER_ID)
    val serviceTypeId: Column<Long> = long(name = SERVICE_TYPE_ID)
    val placeTypeId: Column<Long> = long(name = PLACE_TYPE_ID)
    val minPriceId: Column<Long> = long(name = MIN_PRICE_ID)
    val maxPriceId: Column<Long> = long(name = MAX_PRICE_ID)
    val clientGenderId: Column<Long> = long(name = CLIENT_GENDER_ID)
    val maxClientWeightId: Column<Long> = long(name = MAX_CLIENT_WEIGHT_ID)
    val minClientAge: Column<Int> = integer(name = MIN_CLIENT_AGE)
    val maxClientAge: Column<Int> = integer(name = MAX_CLIENT_AGE)
}