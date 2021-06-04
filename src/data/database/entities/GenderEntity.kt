package backend.data.database.entities

import backend.data.api.models.Gender
import backend.data.database.tables.GendersTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class GenderEntity(private var genderId: EntityID<Long>) : LongEntity(id = genderId) {
    companion object : LongEntityClass<GenderEntity>(GendersTable)
    var gender: String by GendersTable.gender

    override fun toString(): String =
        "Gender(" +
                "gender = $gender" +
                ")"

    fun toGender() =
        Gender(
            genderId = genderId.value,
            gender = gender
        )
}