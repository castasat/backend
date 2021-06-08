package backend.data.database.entities.service

import backend.data.api.models.service.Price
import backend.data.database.tables.service.PricesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class PriceEntity(private var priceId: EntityID<Long>) : LongEntity(id = priceId) {
    companion object : LongEntityClass<PriceEntity>(PricesTable)
    var price: String by PricesTable.price

    override fun toString(): String =
        "Price(" +
                "price = $price" +
                ")"

    fun toPrice() =
        Price(
            priceId = priceId.value,
            price = price
        )
}