package backend.services

import backend.data.models.Street
import backend.data.models.StreetEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class StreetService {
    fun getAllStreets(): Collection<Street> = transaction {
        addLogger(StdOutSqlLogger)
        StreetEntity.all()
            .map { streetEntity: StreetEntity ->
                streetEntity.toStreet()
            }
    }

    fun addStreet(street: Street) = transaction {
        addLogger(StdOutSqlLogger)
        StreetEntity.new {
            this.street = street.street
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteStreet(streetId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        StreetEntity[streetId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getStreet(streetId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        StreetEntity[streetId].toStreet()
    }
}