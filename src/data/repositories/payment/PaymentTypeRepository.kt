package backend.data.repositories.payment

import backend.data.api.models.payment.PaymentType
import backend.data.database.entities.payment.PaymentTypeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class PaymentTypeRepository {
    fun getAllPaymentTypes(): Collection<PaymentType> = transaction {
        addLogger(StdOutSqlLogger)
        PaymentTypeEntity.all()
            .map { paymentTypeEntity: PaymentTypeEntity ->
                paymentTypeEntity.toPaymentType()
            }
    }

    fun addPaymentType(paymentType: PaymentType) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentTypeEntity.new {
            this.paymentType = paymentType.paymentType
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deletePaymentType(paymentTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentTypeEntity[paymentTypeId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getPaymentType(paymentTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PaymentTypeEntity[paymentTypeId].toPaymentType()
    }
}