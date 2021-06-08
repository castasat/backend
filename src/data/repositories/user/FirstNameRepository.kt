package backend.data.repositories.user

import backend.data.api.models.user.FirstName
import backend.data.database.entities.user.FirstNameEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class FirstNameRepository {
    fun getAllFirstNames(): Collection<FirstName> = transaction {
        addLogger(StdOutSqlLogger)
        FirstNameEntity.all()
            .map { firstNameEntity: FirstNameEntity ->
                firstNameEntity.toFirstName()
            }
    }

    fun addFirstName(firstName: FirstName) = transaction {
        addLogger(StdOutSqlLogger)
        FirstNameEntity.new {
            this.firstName = firstName.firstName
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteFirstName(firstNameId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        FirstNameEntity[firstNameId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getFirstName(firstNameId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        FirstNameEntity[firstNameId].toFirstName()
    }
}