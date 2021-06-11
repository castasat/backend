package backend.data.database.entities.service

import backend.data.api.models.service.Order
import backend.data.database.entities.user.UserEntity
import backend.data.database.tables.service.OrdersTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class OrderEntity(
    private var orderId: EntityID<Long>
) : LongEntity(id = orderId) {
    companion object : LongEntityClass<OrderEntity>(OrdersTable)

    var offerId: Long by OrdersTable.offerId
    var clientUserId: Long by OrdersTable.clientUserId

    fun toOrder() = Order(
        orderId = orderId.value,
        offer = OfferEntity[offerId].toOffer(),
        clientUser = UserEntity[clientUserId].toUser()
    )

    override fun toString(): String =
        with(toOrder()) {
            "Order(" +
                    "offer = $offer, " +
                    "clientUser = $clientUser, " +
                    ")"
        }
}