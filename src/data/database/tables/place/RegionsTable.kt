package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object RegionsTable : LongIdTable() {
    private const val REGION = "REGION"
    val region: Column<String> =
        varchar(
            name = REGION,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}