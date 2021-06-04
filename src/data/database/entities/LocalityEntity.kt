package backend.data.database.entities

import backend.data.api.models.Locality
import backend.data.database.tables.LocalitiesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class LocalityEntity(private var localityId: EntityID<Long>) : LongEntity(id = localityId) {
    companion object : LongEntityClass<LocalityEntity>(LocalitiesTable)
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