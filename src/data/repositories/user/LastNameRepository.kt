package backend.data.repositories.user

import backend.data.api.models.user.LastName
import backend.data.database.entities.user.LastNameEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class LastNameRepository {
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