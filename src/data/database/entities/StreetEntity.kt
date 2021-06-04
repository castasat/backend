package backend.data.database.entities

import backend.data.api.models.Street
import backend.data.database.tables.StreetsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class StreetEntity(private var streetId: EntityID<Long>) : LongEntity(id = streetId) {
    companion object : LongEntityClass<StreetEntity>(StreetsTable)
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