package backend.data.repositories.price

import backend.data.api.models.price.Price
import backend.data.database.entities.price.PriceEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class PriceRepository {
    fun getAllPrices(): Collection<Price> = transaction {
        addLogger(StdOutSqlLogger)
        PriceEntity.all()
            .map { priceEntity: PriceEntity ->
                priceEntity.toPrice()
            }
    }

    fun addPrice(price: Price) = transaction {
        addLogger(StdOutSqlLogger)
        PriceEntity.new {
            this.currencyId = price.currency.currencyId
            this.price = price.price
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deletePrice(priceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PriceEntity[priceId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getPrice(priceId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PriceEntity[priceId].toPrice()
    }
}