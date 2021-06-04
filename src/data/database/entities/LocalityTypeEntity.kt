package backend.data.database.entities

import backend.data.api.models.LocalityType
import backend.data.database.tables.LocalityTypesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class LocalityTypeEntity(private var localityTypeId: EntityID<Long>) : LongEntity(id = localityTypeId) {
    companion object : LongEntityClass<LocalityTypeEntity>(LocalityTypesTable)
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