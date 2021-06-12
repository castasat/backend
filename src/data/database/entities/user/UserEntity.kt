package backend.data.database.entities.user

import backend.data.api.models.user.User
import backend.data.database.entities.place.AddressEntity
import backend.data.database.tables.user.UsersTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class UserEntity(
    private var userId: EntityID<Long>
) : LongEntity(id = userId) {
    companion object : LongEntityClass<UserEntity>(UsersTable)

    var firstNameId: Long by UsersTable.firstNameId
    var lastNameId: Long by UsersTable.lastNameId
    var patronymicId: Long by UsersTable.patronymicId
    var genderId: Long by UsersTable.genderId
    var weightId: Long by UsersTable.weightId
    var birthdayId: Long by UsersTable.birthdayId
    var addressId: Long by UsersTable.addressId

    @ExperimentalSerializationApi
    fun toUser() = User(
        userId = userId.value,
        firstName = FirstNameEntity[firstNameId].toFirstName(),
        lastName = LastNameEntity[lastNameId].toLastName(),
        patronymic = PatronymicEntity[patronymicId].toPatronymic(),
        gender = GenderEntity[genderId].toGender(),
        weight = WeightEntity[weightId].toWeight(),
        birthday = BirthdayEntity[birthdayId].toBirthday(),
        address = AddressEntity[addressId].toAddress()
    )

    @ExperimentalSerializationApi
    override fun toString(): String =
        with(toUser()) {
            "User(" +
                    "firstName = $firstName, " +
                    "lastName = $lastName, " +
                    "patronymic = $patronymic, " +
                    "gender = $gender, " +
                    "weight = $weight, " +
                    "birthday = $birthday, " +
                    "address = $address" +
                    ")"
        }
}