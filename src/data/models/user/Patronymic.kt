package backend.data.models.user

import backend.data.CommonConstants
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column


// data class for REST API
@Serializable
data class Patronymic(
    val patronymicId: Long,
    val patronymic: String
)

// JDBC Expose Table with autoincrement long primary key
object PatronymicsTable : LongIdTable() {
    private const val PATRONYMIC = "PATRONYMIC"
    val patronymic: Column<String> =
        varchar(
            name = PATRONYMIC,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class PatronymicEntity(private var patronymicId: EntityID<Long>) : LongEntity(id = patronymicId) {
    companion object : LongEntityClass<PatronymicEntity>(PatronymicsTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var patronymic: String by PatronymicsTable.patronymic

    override fun toString(): String =
        "Patronymic(" +
                "patronymic = $patronymic" +
                ")"

    fun toPatronymic() =
        Patronymic(
            patronymicId = patronymicId.value,
            patronymic = patronymic
        )
}