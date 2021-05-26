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
data class LastName(
    val lastNameId: Long,
    val lastName: String
)

// JDBC Expose Table with autoincrement long primary key
object LastNamesTable : LongIdTable() {
    private const val LAST_NAME = "LAST_NAME"
    val lastName: Column<String> =
        varchar(
            name = LAST_NAME,
            length = MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class LastNameEntity(private var lastNameId: EntityID<Long>) : LongEntity(id = lastNameId) {
    companion object : LongEntityClass<LastNameEntity>(LastNamesTable)

    @Suppress("MemberVisibilityCanBePrivate")
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