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
data class Region(
    val regionId: Long,
    val region: String
)

// JDBC Expose Table with autoincrement long primary key
object RegionsTable : LongIdTable() {
    private const val REGION = "REGION"
    val region: Column<String> =
        varchar(
            name = REGION,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class RegionEntity(private var regionId: EntityID<Long>) : LongEntity(id = regionId) {
    companion object : LongEntityClass<RegionEntity>(RegionsTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var region: String by RegionsTable.region

    override fun toString(): String =
        "Region(" +
                "region = $region" +
                ")"

    fun toRegion() =
        Region(
            regionId = regionId.value,
            region = region
        )
}