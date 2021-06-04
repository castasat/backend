package backend.data.repositories

import backend.data.api.models.Entrance
import backend.data.database.entities.EntranceEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class EntranceRepository {
    fun getAllEntrances(): Collection<Entrance> = transaction {
        addLogger(StdOutSqlLogger)
        EntranceEntity.all()
            .map { entranceEntity: EntranceEntity ->
                entranceEntity.toEntrance()
            }
    }

    fun addEntrance(entrance: Entrance) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceEntity.new {
            this.entrance = entrance.entrance
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteEntrance(entranceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceEntity[entranceId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getEntrance(entranceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceEntity[entranceId].toEntrance()
    }
}