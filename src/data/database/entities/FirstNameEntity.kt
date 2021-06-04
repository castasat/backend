package backend.data.database.entities

import backend.data.api.models.FirstName
import backend.data.database.tables.FirstNamesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class FirstNameEntity(private var firstNameId: EntityID<Long>) : LongEntity(id = firstNameId) {
    companion object : LongEntityClass<FirstNameEntity>(FirstNamesTable)
    var firstName: String by FirstNamesTable.firstName

    override fun toString(): String =
        "FirstName(" +
                "firstName = $firstName" +
                ")"

    fun toFirstName() =
        FirstName(
            firstNameId = firstNameId.value,
            firstName = firstName
        )
}