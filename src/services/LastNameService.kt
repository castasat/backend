package backend.services

import backend.data.models.LastName
import backend.data.models.LastNameEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class LastNameService {
    fun getAllLastNames(): Collection<LastName> = transaction {
        LastNameEntity.all()
            .map { lastNameEntity: LastNameEntity ->
                lastNameEntity.toLastName()
            }
    }

    fun addLastName(lastName: LastName) = transaction {
        LastNameEntity.new {
            this.lastName = lastName.lastName
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteLastName(lastNameId: Long) = transaction {
        LastNameEntity[lastNameId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getLastName(lastNameId: Long) = transaction {
        LastNameEntity[lastNameId].toLastName()
    }
}