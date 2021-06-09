package backend.data.repositories.service

import backend.data.api.models.service.Offer
import backend.data.database.entities.service.OfferEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

@ExperimentalSerializationApi
class OfferRepository {
    fun getAllOffers(): Collection<Offer> = transaction {
        addLogger(StdOutSqlLogger)
        OfferEntity.all()
            .map { offerEntity: OfferEntity ->
                offerEntity.toOffer()
            }
    }

    fun addOffer(offer: Offer) = transaction {
        addLogger(StdOutSqlLogger)
        with(offer) {
            OfferEntity.new {
                this.serviceId = service.serviceId
                this.priceId = price.priceId
                this.addressId = address.addressId
                this.specialistUserId = specialistUser.userId
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteOffer(offerId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OfferEntity[offerId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getOffer(offerId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OfferEntity[offerId].toOffer()
    }
}