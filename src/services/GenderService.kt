package backend.services

import backend.data.models.Gender
import backend.data.models.GenderEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class GenderService {
    fun getAllGenders(): Collection<Gender> = transaction {
        addLogger(StdOutSqlLogger)
        GenderEntity.all()
            .map { genderEntity: GenderEntity ->
                genderEntity.toGender()
            }
    }

    fun addGender(gender: Gender) = transaction {
        addLogger(StdOutSqlLogger)
        GenderEntity.new {
            this.gender = gender.gender
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteGender(genderId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        GenderEntity[genderId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getGender(genderId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        GenderEntity[genderId].toGender()
    }
}