package backend.data.models.user

import backend.data.CommonConstants.MAX_VARCHAR_LENGTH_CHARS
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// data class for REST API
@Serializable
data class Gender(
    val genderId: Long,
    val gender: String
)

// JDBC Expose Table with autoincrement long primary key
object GendersTable : LongIdTable() {
    private const val GENDER = "GENDER"
    val gender: Column<String> =
        varchar(
            name = GENDER,
            length = MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class GenderEntity(private var genderId: EntityID<Long>) : LongEntity(id = genderId) {
    companion object : LongEntityClass<GenderEntity>(GendersTable)

    @Suppress("MemberVisibilityCanBePrivate")
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