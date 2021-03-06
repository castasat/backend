package backend.data.database.entities.place

import backend.data.api.models.place.Building
import backend.data.database.tables.place.BuildingsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class BuildingEntity(private var buildingId: EntityID<Long>) : LongEntity(id = buildingId) {
    companion object : LongEntityClass<BuildingEntity>(BuildingsTable)
    var building: String by BuildingsTable.building

    override fun toString(): String =
        "Building(" +
                "building = $building" +
                ")"

    fun toBuilding() =
        Building(
            buildingId = buildingId.value,
            building = building
        )
}