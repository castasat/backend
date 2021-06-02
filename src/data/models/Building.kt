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
data class Building(
    val buildingId: Long,
    val building: String
)

// JDBC Expose Table with autoincrement long primary key
object BuildingsTable : LongIdTable() {
    private const val BUILDING = "BUILDING"
    val building: Column<String> =
        varchar(
            name = BUILDING,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class BuildingEntity(private var buildingId: EntityID<Long>) : LongEntity(id = buildingId) {
    companion object : LongEntityClass<BuildingEntity>(BuildingsTable)

    @Suppress("MemberVisibilityCanBePrivate")
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