package backend.data.database.tables.service

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object ServicesTable : LongIdTable() {
    private const val SERVICE_TYPE_ID = "SERVICE_TYPE_ID"
    private const val SERVICE_DURATION = "SERVICE_DURATION"

    val serviceTypeId: Column<Long> = long(
        name = SERVICE_TYPE_ID
    )
    val serviceDuration: Column<String> = varchar(
        name = SERVICE_DURATION,
        length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
    )
}