package backend.data.database.entities

import backend.data.api.models.Region
import backend.data.database.tables.RegionsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class RegionEntity(private var regionId: EntityID<Long>) : LongEntity(id = regionId) {
    companion object : LongEntityClass<RegionEntity>(RegionsTable)
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