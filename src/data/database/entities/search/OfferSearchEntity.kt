package backend.data.database.entities.search

import backend.data.api.models.search.OfferSearch
import backend.data.database.entities.payment.PaymentTypeEntity
import backend.data.database.entities.place.PlaceTypeEntity
import backend.data.database.entities.price.PriceEntity
import backend.data.database.entities.service.ServiceTypeEntity
import backend.data.database.entities.user.GenderEntity
import backend.data.database.entities.user.UserEntity
import backend.data.database.tables.search.OfferSearchesTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class OfferSearchEntity(
    private var offerSearchId: EntityID<Long>
) : LongEntity(id = offerSearchId) {
    companion object : LongEntityClass<OfferSearchEntity>(OfferSearchesTable)

    var clientUserId: Long by OfferSearchesTable.clientUserId
    var serviceTypeId: Long by OfferSearchesTable.serviceTypeId
    var placeTypeId: Long by OfferSearchesTable.placeTypeId
    var minPriceId: Long by OfferSearchesTable.minPriceId
    var maxPriceId: Long by OfferSearchesTable.maxPriceId
    var paymentTypeId: Long by OfferSearchesTable.paymentTypeId
    var specialistGenderId: Long by OfferSearchesTable.specialistGenderId

    fun toOfferSearch() = OfferSearch(
        offerSearchId = offerSearchId.value,
        clientUser = UserEntity[clientUserId].toUser(),
        serviceType = ServiceTypeEntity[serviceTypeId].toServiceType(),
        placeType = PlaceTypeEntity[placeTypeId].toPlaceType(),
        minPrice = PriceEntity[minPriceId].toPrice(),
        maxPrice = PriceEntity[maxPriceId].toPrice(),
        paymentType = PaymentTypeEntity[paymentTypeId].toPaymentType(),
        specialistGender = GenderEntity[specialistGenderId].toGender()
    )

    override fun toString(): String =
        with(toOfferSearch()) {
            "OfferSearch(" +
                    "clientUser = $clientUser, " +
                    "serviceType = $serviceType, " +
                    "placeType = $placeType, " +
                    "minPrice = $minPrice, " +
                    "maxPrice = $maxPrice, " +
                    "paymentType = $paymentType, " +
                    "specialistGender = $specialistGender" +
                    ")"
        }
}