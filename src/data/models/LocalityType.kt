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
data class LocalityType(
    val localityTypeId: Long,
    val localityType: String
)

// JDBC Expose Table with autoincrement long primary key
object LocalityTypesTable : LongIdTable() {
    private const val LOCALITY_TYPE = "LOCALITY_TYPE"
    val localityType: Column<String> =
        varchar(
            name = LOCALITY_TYPE,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class LocalityTypeEntity(private var localityTypeId: EntityID<Long>) : LongEntity(id = localityTypeId) {
    companion object : LongEntityClass<LocalityTypeEntity>(LocalityTypesTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var localityType: String by LocalityTypesTable.localityType

    override fun toString(): String =
        "LocalityType(" +
                "localityType = $localityType" +
                ")"

    fun toLocalityType() =
        LocalityType(
            localityTypeId = localityTypeId.value,
            localityType = localityType
        )
}