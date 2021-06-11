package backend.data.repositories.service

import backend.data.api.models.service.Order
import backend.data.database.entities.service.OrderEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

@ExperimentalSerializationApi
class OrderRepository {
    fun getAllOrders(): Collection<Order> = transaction {
        addLogger(StdOutSqlLogger)
        OrderEntity.all()
            .map { orderEntity: OrderEntity ->
                orderEntity.toOrder()
            }
    }

    fun addOrder(order: Order) = transaction {
        addLogger(StdOutSqlLogger)
        with(order) {
            OrderEntity.new {
                this.offerId = offer.offerId
                this.clientUserId = clientUser.userId
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteOrder(orderId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OrderEntity[orderId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getOrder(orderId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OrderEntity[orderId].toOrder()
    }
}