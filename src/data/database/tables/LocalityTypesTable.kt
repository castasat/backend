package backend.data.database.tables

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object LocalityTypesTable : LongIdTable() {
    private const val LOCALITY_TYPE = "LOCALITY_TYPE"
    val localityType: Column<String> =
        varchar(
            name = LOCALITY_TYPE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}