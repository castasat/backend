package backend.data.database.entities.payment

import backend.data.api.models.payment.PaymentType
import backend.data.database.tables.payment.PaymentTypesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class PaymentTypeEntity(private var paymentTypeId: EntityID<Long>) : LongEntity(id = paymentTypeId) {
    companion object : LongEntityClass<PaymentTypeEntity>(PaymentTypesTable)

    var paymentType: String by PaymentTypesTable.paymentType

    override fun toString(): String =
        "PaymentType(" +
                "paymentType = $paymentType" +
                ")"

    fun toPaymentType() =
        PaymentType(
            paymentTypeId = paymentTypeId.value,
            paymentType = paymentType
        )
}