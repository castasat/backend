package backend.services

import backend.data.models.Birthday
import backend.data.models.BirthdayEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class BirthdayService {
    fun getAllBirthdays(): Collection<Birthday> = transaction {
        addLogger(StdOutSqlLogger)
        BirthdayEntity.all()
            .map { birthdayEntity: BirthdayEntity ->
                birthdayEntity.toBirthday()
            }
    }

    fun addBirthday(birthday: Birthday) = transaction {
        addLogger(StdOutSqlLogger)
        BirthdayEntity.new {
            this.birthday = birthday.birthday
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteBirthday(birthdayId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        BirthdayEntity[birthdayId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getBirthday(birthdayId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        BirthdayEntity[birthdayId].toBirthday()
    }
}