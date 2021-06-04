package backend.services.address

import backend.data.models.address.EntranceCode
import backend.data.models.address.EntranceCodeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class EntranceCodeService {
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