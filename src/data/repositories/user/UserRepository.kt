package backend.data.repositories.user

import backend.data.api.models.user.User
import backend.data.database.entities.user.UserEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class UserRepository {
    @ExperimentalSerializationApi
    fun getAllUsers(): Collection<User> = transaction {
        addLogger(StdOutSqlLogger)
        UserEntity.all()
            .map { userEntity: UserEntity ->
                userEntity.toUser()
            }
    }

    @ExperimentalSerializationApi
    fun addUser(user: User) = transaction {
        addLogger(StdOutSqlLogger)
        with(user) {
            UserEntity.new {
                this.firstNameId = firstName.firstNameId
                this.lastNameId = lastName.lastNameId
                this.patronymicId = patronymic.patronymicId
                this.genderId = gender.genderId
                this.weightId = weight.weightId
                this.birthdayId = birthday.birthdayId
                this.addressId = address.addressId
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteUser(userId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        UserEntity[userId].delete()
    }

    @ExperimentalSerializationApi
    @Throws(EntityNotFoundException::class)
    fun getUser(userId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        UserEntity[userId].toUser()
    }
}