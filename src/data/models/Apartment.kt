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
data class Apartment(
    val apartmentId: Long,
    val apartment: String
)

// JDBC Expose Table with autoincrement long primary key
object ApartmentsTable : LongIdTable() {
    private const val APARTMENT = "APARTMENT"
    val apartment: Column<String> =
        varchar(
            name = APARTMENT,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class ApartmentEntity(private var apartmentId: EntityID<Long>) : LongEntity(id = apartmentId) {
    companion object : LongEntityClass<ApartmentEntity>(ApartmentsTable)

    @Suppress("MemberVisibilityCanBePrivate")
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