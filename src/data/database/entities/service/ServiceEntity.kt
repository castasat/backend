package backend.data.database.entities.service

import backend.data.api.models.service.Service
import backend.data.database.tables.service.ServicesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class ServiceEntity(
    private var serviceId: EntityID<Long>
) : LongEntity(id = serviceId) {
    companion object : LongEntityClass<ServiceEntity>(ServicesTable)

    var serviceTypeId: Long by ServicesTable.serviceTypeId
    var serviceDuration: String by ServicesTable.serviceDuration

    fun toService() = Service(
        serviceId = serviceId.value,
        serviceType = ServiceTypeEntity[serviceTypeId].toServiceType(),
        serviceDuration = serviceDuration
    )

    override fun toString(): String =
        with(toService()) {
            "Service(" +
                    "serviceType = $serviceType, " +
                    "serviceDuration = $serviceDuration" +
                    ")"
        }
}