package backend.data.database.entities.payment

import backend.data.api.models.payment.Payment
import backend.data.database.entities.service.OrderEntity
import backend.data.database.tables.payment.PaymentsTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class PaymentEntity(private var paymentId: EntityID<Long>) : LongEntity(id = paymentId) {
    companion object : LongEntityClass<PaymentEntity>(PaymentsTable)

    var orderId: Long by PaymentsTable.orderId
    var paymentTypeId: Long by PaymentsTable.paymentTypeId
    var paymentAmount: String by PaymentsTable.paymentAmount

    fun toPayment() = Payment(
        paymentId = paymentId.value,
        order = OrderEntity[orderId].toOrder(),
        paymentType = PaymentTypeEntity[paymentTypeId].toPaymentType(),
        paymentAmount = paymentAmount
    )

    override fun toString(): String =
        with(toPayment()) {
            "Payment(" +
                    "order = $order, " +
                    "paymentType = $paymentType, " +
                    "paymentAmount = $paymentAmount" +
                    ")"
        }
}