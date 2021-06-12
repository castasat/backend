package backend.data.database.tables.user

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object WeightsTable : LongIdTable() {
    private const val WEIGHT = "WEIGHT"
    val weight: Column<String> =
        varchar(
            name = WEIGHT,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}