package backend.data.models.address

import backend.data.CommonConstants
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// data class for REST API
@Serializable
data class EntranceCode(
    val entranceCodeId: Long,
    val entranceCode: String
)

// JDBC Expose Table with autoincrement long primary key
object EntranceCodesTable : LongIdTable() {
    private const val ENTRANCE_CODE = "ENTRANCE_CODE"
    val entranceCode: Column<String> =
        varchar(
            name = ENTRANCE_CODE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class EntranceCodeEntity(private var entranceCodeId: EntityID<Long>) : LongEntity(id = entranceCodeId) {
    companion object : LongEntityClass<EntranceCodeEntity>(EntranceCodesTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var entranceCode: String by EntranceCodesTable.entranceCode

    override fun toString(): String =
        "EntranceCode(" +
                "entranceCode = $entranceCode" +
                ")"

    fun toEntranceCode() =
        EntranceCode(
            entranceCodeId = entranceCodeId.value,
            entranceCode = entranceCode
        )
}