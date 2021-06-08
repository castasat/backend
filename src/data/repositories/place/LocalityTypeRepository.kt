package backend.data.repositories.place

import backend.data.api.models.place.LocalityType
import backend.data.database.entities.place.LocalityTypeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class LocalityTypeRepository {
    fun getAllLocalityTypes(): Collection<LocalityType> = transaction {
        addLogger(StdOutSqlLogger)
        LocalityTypeEntity.all()
            .map { localityTypeEntity: LocalityTypeEntity ->
                localityTypeEntity.toLocalityType()
            }
    }

    fun addLocalityType(localityType: LocalityType) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityTypeEntity.new {
            this.localityType = localityType.localityType
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteLocalityType(localityTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityTypeEntity[localityTypeId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getLocalityType(localityTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityTypeEntity[localityTypeId].toLocalityType()
    }
}