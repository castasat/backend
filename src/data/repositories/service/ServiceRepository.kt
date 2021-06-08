package backend.data.repositories.service

import backend.data.api.models.service.Service
import backend.data.database.entities.service.ServiceEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class ServiceRepository {
    fun getAllServices(): Collection<Service> = transaction {
        addLogger(StdOutSqlLogger)
        ServiceEntity.all()
            .map { serviceEntity: ServiceEntity ->
                serviceEntity.toService()
            }
    }

    fun addService(service: Service) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceEntity.new {
            this.serviceTypeId = service.serviceType.serviceTypeId
            this.serviceDuration = service.serviceDuration
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteService(serviceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceEntity[serviceId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getService(serviceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ServiceEntity[serviceId].toService()
    }
}