package backend.services

import backend.data.models.Country
import backend.data.models.CountryEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class CountryService {
    fun getAllCountries(): Collection<Country> = transaction {
        addLogger(StdOutSqlLogger)
        CountryEntity.all()
            .map { countryEntity: CountryEntity ->
                countryEntity.toCountry()
            }
    }

    fun addCountry(country: Country) = transaction {
        addLogger(StdOutSqlLogger)
        CountryEntity.new {
            this.country = country.country
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteCountry(countryId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CountryEntity[countryId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getCountry(countryId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CountryEntity[countryId].toCountry()
    }
}