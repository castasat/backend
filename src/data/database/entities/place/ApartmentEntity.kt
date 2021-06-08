package backend.data.database.entities.place

import backend.data.api.models.place.Apartment
import backend.data.database.tables.place.ApartmentsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class ApartmentEntity(private var apartmentId: EntityID<Long>) : LongEntity(id = apartmentId) {
    companion object : LongEntityClass<ApartmentEntity>(ApartmentsTable)
    var apartment: String by ApartmentsTable.apartment

    override fun toString(): String =
        "Apartment(" +
                "apartment = $apartment" +
                ")"

    fun toApartment() =
        Apartment(
            apartmentId = apartmentId.value,
            apartment = apartment
        )
}