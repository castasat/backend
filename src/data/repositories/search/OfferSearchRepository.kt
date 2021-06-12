package backend.data.repositories.search

import backend.data.api.models.search.OfferSearch
import backend.data.database.entities.search.OfferSearchEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

@ExperimentalSerializationApi
class OfferSearchRepository {

    fun getAllOfferSearches(): Collection<OfferSearch> = transaction {
        addLogger(StdOutSqlLogger)
        OfferSearchEntity.all()
            .map { offerSearchEntity: OfferSearchEntity ->
                offerSearchEntity.toOfferSearch()
            }
    }

    fun addOfferSearch(offerSearch: OfferSearch) = transaction {
        addLogger(StdOutSqlLogger)
        with(offerSearch) {
            OfferSearchEntity.new {
                this.clientUserId = clientUser.userId
                this.serviceTypeId = serviceType.serviceTypeId
                this.placeTypeId = placeType.placeTypeId
                this.minPriceId = minPrice.priceId
                this.maxPriceId = maxPrice.priceId
                this.paymentTypeId = paymentType.paymentTypeId
                this.specialistGenderId = specialistGender.genderId
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteOfferSearch(offerSearchId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OfferSearchEntity[offerSearchId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getOfferSearch(offerSearchId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        OfferSearchEntity[offerSearchId].toOfferSearch()
    }
}