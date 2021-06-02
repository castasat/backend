package backend.services.user

import backend.data.models.user.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class LastNameService {
    fun getAllLastNames(): Collection<LastName> = transaction {
        addLogger(StdOutSqlLogger)
        LastNameEntity.all()
            .map { lastNameEntity: LastNameEntity ->
                lastNameEntity.toLastName()
            }
    }

    fun addLastName(lastName: LastName) = transaction {
        addLogger(StdOutSqlLogger)
        LastNameEntity.new {
            this.lastName = lastName.lastName
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteLastName(lastNameId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LastNameEntity[lastNameId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getLastName(lastNameId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LastNameEntity[lastNameId].toLastName()
    }
}