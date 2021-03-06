package backend.data.database.entities.price

import backend.data.api.models.price.Currency
import backend.data.database.tables.price.CurrenciesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class CurrencyEntity(private var currencyId: EntityID<Long>) : LongEntity(id = currencyId) {
    companion object : LongEntityClass<CurrencyEntity>(CurrenciesTable)
    var currency: String by CurrenciesTable.currency

    override fun toString(): String =
        "Currency(" +
                "currency = $currency" +
                ")"

    fun toCurrency() =
        Currency(
            currencyId = currencyId.value,
            currency = currency
        )
}