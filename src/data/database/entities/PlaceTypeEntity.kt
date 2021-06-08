package backend.data.database.entities

import backend.data.api.models.PlaceType
import backend.data.database.tables.PlaceTypesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class PlaceTypeEntity(private var placeTypeId: EntityID<Long>) : LongEntity(id = placeTypeId) {
    companion object : LongEntityClass<PlaceTypeEntity>(PlaceTypesTable)

    var placeType: String by PlaceTypesTable.placeType

    override fun toString(): String =
        "PlaceType(" +
                "placeType = $placeType" +
                ")"

    fun toPlaceType() =
        PlaceType(
            placeTypeId = placeTypeId.value,
            placeType = placeType
        )
}