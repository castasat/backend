package backend.data.repositories

import backend.data.database.entities.ApartmentEntity
import backend.data.api.models.Apartment
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class ApartmentRepository {
    fun getAllApartments(): Collection<Apartment> = transaction {
        addLogger(StdOutSqlLogger)
        ApartmentEntity.all()
            .map { apartmentEntity: ApartmentEntity ->
                apartmentEntity.toApartment()
            }
    }

    fun addApartment(apartment: Apartment) = transaction {
        addLogger(StdOutSqlLogger)
        ApartmentEntity.new {
            this.apartment = apartment.apartment
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteApartment(apartmentId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ApartmentEntity[apartmentId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getApartment(apartmentId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ApartmentEntity[apartmentId].toApartment()
    }
}