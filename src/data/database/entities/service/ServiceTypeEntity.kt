package backend.data.database.entities.service

import backend.data.api.models.service.ServiceType
import backend.data.database.tables.service.ServiceTypesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class ServiceTypeEntity(private var serviceTypeId: EntityID<Long>) : LongEntity(id = serviceTypeId) {
    companion object : LongEntityClass<ServiceTypeEntity>(ServiceTypesTable)

    var serviceType: String by ServiceTypesTable.serviceType

    override fun toString(): String =
        "ServiceType(" +
                "serviceType = $serviceType" +
                ")"

    fun toServiceType() =
        ServiceType(
            serviceTypeId = serviceTypeId.value,
            serviceType = serviceType
        )
}