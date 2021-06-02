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
data class Locality(
    val localityId: Long,
    val locality: String
)

// JDBC Expose Table with autoincrement long primary key
object LocalitiesTable : LongIdTable() {
    private const val LOCALITY = "LOCALITY"
    val locality: Column<String> =
        varchar(
            name = LOCALITY,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class LocalityEntity(private var localityId: EntityID<Long>) : LongEntity(id = localityId) {
    companion object : LongEntityClass<LocalityEntity>(LocalitiesTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var locality: String by LocalitiesTable.locality

    override fun toString(): String =
        "Locality(" +
                "locality = $locality" +
                ")"

    fun toLocality() =
        Locality(
            localityId = localityId.value,
            locality = locality
        )
}