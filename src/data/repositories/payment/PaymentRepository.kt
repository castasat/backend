package backend.data.repositories.payment

import backend.data.api.models.payment.Payment
import backend.data.database.entities.payment.PaymentEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

@ExperimentalSerializationApi
class PaymentRepository {
    fun getAllPayments(): Collection<Payment> = transaction {
        addLogger(StdOutSqlLogger)
        PaymentEntity.all()
            .map { paymentEntity: PaymentEntity ->
                paymentEntity.toPayment()
            }
    }

    fun addPayment(payment: Payment) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentEntity.new {
            this.orderId = payment.order.orderId
            this.paymentTypeId = payment.paymentType.paymentTypeId
            this.paymentAmount = payment.paymentAmount
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deletePayment(paymentId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentEntity[paymentId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getPayment(paymentId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentEntity[paymentId].toPayment()
    }
}