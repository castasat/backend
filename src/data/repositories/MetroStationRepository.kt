package backend.data.repositories

import backend.data.api.models.MetroStation
import backend.data.database.entities.MetroStationEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class MetroStationRepository {
    fun getAllMetroStations(): Collection<MetroStation> = transaction {
        addLogger(StdOutSqlLogger)
        MetroStationEntity.all()
            .map { metroStationEntity: MetroStationEntity ->
                metroStationEntity.toMetroStation()
            }
    }

    fun addMetroStation(metroStation: MetroStation) = transaction {
        addLogger(StdOutSqlLogger)
        MetroStationEntity.new {
            this.metroStation = metroStation.metroStation
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteMetroStation(metroStationId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        MetroStationEntity[metroStationId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getMetroStation(metroStationId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        MetroStationEntity[metroStationId].toMetroStation()
    }
}