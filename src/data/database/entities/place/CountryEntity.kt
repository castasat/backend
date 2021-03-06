package backend.data.database.entities.place

import backend.data.api.models.place.Country
import backend.data.database.tables.place.CountriesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class CountryEntity(private var countryId: EntityID<Long>) : LongEntity(id = countryId) {
    companion object : LongEntityClass<CountryEntity>(CountriesTable)
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