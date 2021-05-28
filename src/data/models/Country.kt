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
data class Country(
    val countryId: Long,
    val country: String
)

// JDBC Expose Table with autoincrement long primary key
object CountriesTable : LongIdTable() {
    private const val COUNTRY = "COUNTRY"
    val country: Column<String> =
        varchar(
            name = COUNTRY,
            length = CommonConstants.MAX_VARCHAR_LENGTH_CHARS
        )
}

// JDBC Expose DAO Entity representing Row in a Table
class CountryEntity(private var countryId: EntityID<Long>) : LongEntity(id = countryId) {
    companion object : LongEntityClass<CountryEntity>(CountriesTable)

    @Suppress("MemberVisibilityCanBePrivate")
    var country: String by CountriesTable.country

    override fun toString(): String =
        "Country(" +
                "country = $country" +
                ")"

    fun toCountry() =
        Country(
            countryId = countryId.value,
            country = country
        )
}