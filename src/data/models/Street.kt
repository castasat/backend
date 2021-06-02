package backend.data.models

import backend.data.CommonConstants
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// data class for REST API
@Serializable
data class Street(
    val streetId: Long,
    val street: String
)

// JDBC Expose Table with autoincrement long primary key
object StreetsTable : LongIdTable() {
    private const val STREET = "STREET"
    val street: Column<String> =
        varchar(
            name = STREET,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class StreetEntity(private var streetId: EntityID<Long>) : LongEntity(id = streetId) {
    companion object : LongEntityClass<StreetEntity>(StreetsTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var street: String by StreetsTable.street

    override fun toString(): String =
        "Street(" +
                "street = $street" +
                ")"

    fun toStreet() =
        Street(
            streetId = streetId.value,
            street = street
        )
}