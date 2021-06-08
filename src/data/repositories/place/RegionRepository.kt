package backend.data.repositories.place

import backend.data.api.models.place.Region
import backend.data.database.entities.place.RegionEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class RegionRepository {
    fun getAllRegions(): Collection<Region> = transaction {
        addLogger(StdOutSqlLogger)
        RegionEntity.all()
            .map { regionEntity: RegionEntity ->
                regionEntity.toRegion()
            }
    }

    fun addRegion(region: Region) = transaction {
        addLogger(StdOutSqlLogger)
        RegionEntity.new {
            this.region = region.region
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteRegion(regionId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        RegionEntity[regionId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getRegion(regionId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        RegionEntity[regionId].toRegion()
    }
}