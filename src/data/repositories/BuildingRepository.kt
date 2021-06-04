package backend.data.repositories

import backend.data.api.models.Building
import backend.data.database.entities.BuildingEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class BuildingRepository {
    fun getAllBuildings(): Collection<Building> = transaction {
        addLogger(StdOutSqlLogger)
        BuildingEntity.all()
            .map { buildingEntity: BuildingEntity ->
                buildingEntity.toBuilding()
            }
    }

    fun addBuilding(building: Building) = transaction {
        addLogger(StdOutSqlLogger)
        BuildingEntity.new {
            this.building = building.building
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteBuilding(buildingId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        BuildingEntity[buildingId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getBuilding(buildingId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        BuildingEntity[buildingId].toBuilding()
    }
}