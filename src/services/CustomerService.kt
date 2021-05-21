package backend.services

import backend.models.Customer
import backend.models.CustomerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.jvm.Throws

class CustomerService {
    fun getAllCustomers(): Collection<Customer> = transaction {
        CustomerEntity.all()
            .map { customerEntity: CustomerEntity ->
                customerEntity.toCustomer()
            }
    }

    fun addCustomer(customer: Customer) = transaction {
        CustomerEntity.new {
            firstName = customer.firstName
            lastName = customer.lastName
            email = customer.email
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteCustomer(customerId: Int) = transaction {
        CustomerEntity[customerId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getCustomer(customerId: Int) = transaction {
        CustomerEntity[customerId].toCustomer()
    }
}

fun DI.MainBuilder.bindServices() {
    bind<CustomerService>() with singleton { CustomerService() }
}

// TODO
suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }