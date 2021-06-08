package backend.data.repositories.payment

import backend.data.api.models.payment.Currency
import backend.data.database.entities.payment.CurrencyEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class CurrencyRepository {
    fun getAllCurrencies(): Collection<Currency> = transaction {
        addLogger(StdOutSqlLogger)
        CurrencyEntity.all()
            .map { currencyEntity: CurrencyEntity ->
                currencyEntity.toCurrency()
            }
    }

    fun addCurrency(currency: Currency) = transaction {
        addLogger(StdOutSqlLogger)
        CurrencyEntity.new {
            this.currency = currency.currency
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteCurrency(currencyId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CurrencyEntity[currencyId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getCurrency(currencyId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CurrencyEntity[currencyId].toCurrency()
    }
}