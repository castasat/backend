package backend.data.database.entities.service

import backend.data.api.models.service.Offer
import backend.data.database.entities.place.AddressEntity
import backend.data.database.entities.price.PriceEntity
import backend.data.database.entities.user.UserEntity
import backend.data.database.tables.service.OffersTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class OfferEntity(
    private var offerId: EntityID<Long>
) : LongEntity(id = offerId) {
    companion object : LongEntityClass<OfferEntity>(OffersTable)

    var serviceId: Long by OffersTable.serviceId
    var priceId: Long by OffersTable.priceId
    var addressId: Long by OffersTable.addressId
    var specialistUserId: Long by OffersTable.specialistUserId

    fun toOffer() = Offer(
        offerId = offerId.value,
        service = ServiceEntity[serviceId].toService(),
        price = PriceEntity[priceId].toPrice(),
        address = AddressEntity[addressId].toAddress(),
        specialistUser = UserEntity[specialistUserId].toUser()
    )

    override fun toString(): String =
        with(toOffer()){
            "Offer(" +
                    "service = $service, " +
                    "price = $price, " +
                    "address = $address, " +
                    "specialistUser = $specialistUser" +
                    ")"
        }
}