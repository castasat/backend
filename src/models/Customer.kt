package backend.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

// data class for REST API
@Serializable
data class Customer(
    val firstName: String,
    val lastName: String,
    val email: String
)

// JDBC Expose Table with autoincrement integer primary key
object Customers : IntIdTable() {
    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val EMAIL = "EMAIL"
    private const val MAX_CHARS_LENGTH = 255

    val firstName = varchar(name = FIRST_NAME, length = MAX_CHARS_LENGTH)
    val lastName = varchar(name = LAST_NAME, length = MAX_CHARS_LENGTH)
    val email = varchar(name = EMAIL, length = MAX_CHARS_LENGTH)
}

// JDBC Expose DAO Entity representing Row in a Table
class CustomerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerEntity>(Customers)

    var firstName by Customers.firstName
    var lastName by Customers.lastName
    var email by Customers.email

    override fun toString() =
        "Customer(" +
                "firstName = $firstName, " +
                "lastName = $lastName, " +
                "email = $email" +
                ")"

    fun toCustomer() = Customer(
        firstName = firstName,
        lastName = lastName,
        email = email
    )
}