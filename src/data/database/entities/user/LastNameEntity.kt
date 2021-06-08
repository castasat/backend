package backend.data.database.entities.user

import backend.data.api.models.user.LastName
import backend.data.database.tables.user.LastNamesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class LastNameEntity(private var lastNameId: EntityID<Long>) : LongEntity(id = lastNameId) {
    companion object : LongEntityClass<LastNameEntity>(LastNamesTable)
    var lastName: String by LastNamesTable.lastName

    override fun toString(): String =
        "LastName(" +
                "lastName = $lastName" +
                ")"

    fun toLastName() =
        LastName(
            lastNameId = lastNameId.value,
            lastName = lastName
        )
}