package backend.data.database.entities.price

import backend.data.api.models.price.Price
import backend.data.database.tables.price.PricesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class PriceEntity(private var priceId: EntityID<Long>) : LongEntity(id = priceId) {
    companion object : LongEntityClass<PriceEntity>(PricesTable)

    var currencyId: Long by PricesTable.currencyId
    var price: String by PricesTable.price

    fun toPrice() = Price(
        priceId = priceId.value,
        currency = CurrencyEntity[currencyId].toCurrency(),
        price = price
    )

    override fun toString(): String =
        with(toPrice()){
            "Price(" +
                    "currency = $currency" +
                    "price = $price" +
                    ")"
        }
}