package backend.services.address

import backend.data.models.address.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class RegionService {
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