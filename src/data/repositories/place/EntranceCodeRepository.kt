package backend.data.repositories.place

import backend.data.api.models.place.EntranceCode
import backend.data.database.entities.place.EntranceCodeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class EntranceCodeRepository {
    fun getAllEntranceCodes(): Collection<EntranceCode> = transaction {
        addLogger(StdOutSqlLogger)
        EntranceCodeEntity.all()
            .map { entranceCodeEntity: EntranceCodeEntity ->
                entranceCodeEntity.toEntranceCode()
            }
    }

    fun addEntranceCode(entranceCode: EntranceCode) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceCodeEntity.new {
            this.entranceCode = entranceCode.entranceCode
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteEntranceCode(entranceCodeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceCodeEntity[entranceCodeId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getEntranceCode(entranceCodeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        EntranceCodeEntity[entranceCodeId].toEntranceCode()
    }
}