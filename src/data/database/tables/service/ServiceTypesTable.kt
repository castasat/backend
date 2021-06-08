package backend.data.database.tables.service

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object ServiceTypesTable : LongIdTable() {
    private const val SERVICE_TYPE = "SERVICE_TYPE"
    val serviceType: Column<String> =
        varchar(
            name = SERVICE_TYPE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}