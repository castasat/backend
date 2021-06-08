package backend.data.repositories.service

import backend.data.api.models.service.ServiceType
import backend.data.database.entities.service.ServiceTypeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class ServiceTypeRepository {
    fun getAllServiceTypes(): Collection<ServiceType> = transaction {
        addLogger(StdOutSqlLogger)
        ServiceTypeEntity.all()
            .map { serviceTypeEntity: ServiceTypeEntity ->
                serviceTypeEntity.toServiceType()
            }
    }

    fun addServiceType(serviceType: ServiceType) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceTypeEntity.new {
            this.serviceType = serviceType.serviceType
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteServiceType(serviceTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceTypeEntity[serviceTypeId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getServiceType(serviceTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceTypeEntity[serviceTypeId].toServiceType()
    }
}