package backend.services.user

import backend.data.models.user.Patronymic
import backend.data.models.user.PatronymicEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class PatronymicService {
    fun getAllPatronymics(): Collection<Patronymic> = transaction {
        addLogger(StdOutSqlLogger)
        PatronymicEntity.all()
            .map { patronymicEntity: PatronymicEntity ->
                patronymicEntity.toPatronymic()
            }
    }

    fun addPatronymic(patronymic: Patronymic) = transaction {
        addLogger(StdOutSqlLogger)
        PatronymicEntity.new {
            this.patronymic = patronymic.patronymic
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deletePatronymic(patronymicId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PatronymicEntity[patronymicId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getPatronymic(patronymicId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PatronymicEntity[patronymicId].toPatronymic()
    }
}