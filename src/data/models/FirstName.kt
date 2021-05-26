package backend.data.models

import backend.data.CommonConstants.MAX_VARCHAR_LENGTH_CHARS
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// data class for REST API
@Serializable
data class FirstName(
    val firstNameId: Long,
    val firstName: String
)

// JDBC Expose Table with autoincrement long primary key
object FirstNamesTable : LongIdTable() {
    private const val FIRST_NAME = "FIRST_NAME"
    val firstName: Column<String> =
        varchar(
            name = FIRST_NAME,
            length = MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class FirstNameEntity(private var firstNameId: EntityID<Long>) : LongEntity(id = firstNameId) {
    companion object : LongEntityClass<FirstNameEntity>(FirstNamesTable)

    @Suppress("MemberVisibilityCanBePrivate")
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