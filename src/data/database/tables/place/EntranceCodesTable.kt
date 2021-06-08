package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object EntranceCodesTable : LongIdTable() {
    private const val ENTRANCE_CODE = "ENTRANCE_CODE"
    val entranceCode: Column<String> =
        varchar(
            name = ENTRANCE_CODE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}