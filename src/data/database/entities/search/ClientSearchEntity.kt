package backend.data.database.entities.search

import backend.data.api.models.search.ClientSearch
import backend.data.database.entities.place.PlaceTypeEntity
import backend.data.database.entities.price.PriceEntity
import backend.data.database.entities.service.ServiceTypeEntity
import backend.data.database.entities.user.GenderEntity
import backend.data.database.entities.user.UserEntity
import backend.data.database.entities.user.WeightEntity
import backend.data.database.tables.search.ClientSearchesTable
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
@ExperimentalSerializationApi
class ClientSearchEntity(
    private var clientSearchId: EntityID<Long>
) : LongEntity(id = clientSearchId) {
    companion object : LongEntityClass<ClientSearchEntity>(ClientSearchesTable)

    var specialistUserId: Long by ClientSearchesTable.specialistUserId
    var serviceTypeId: Long by ClientSearchesTable.serviceTypeId
    var placeTypeId: Long by ClientSearchesTable.placeTypeId
    var minPriceId: Long by ClientSearchesTable.minPriceId
    var maxPriceId: Long by ClientSearchesTable.maxPriceId
    var clientGenderId: Long by ClientSearchesTable.clientGenderId
    var maxClientWeightId: Long by ClientSearchesTable.maxClientWeightId
    var minClientAge: Int by ClientSearchesTable.minClientAge
    var maxClientAge: Int by ClientSearchesTable.maxClientAge

    fun toClientSearch() = ClientSearch(
        clientSearchId = clientSearchId.value,
        specialistUser = UserEntity[specialistUserId].toUser(),
        serviceType = ServiceTypeEntity[serviceTypeId].toServiceType(),
        placeType = PlaceTypeEntity[placeTypeId].toPlaceType(),
        minPrice = PriceEntity[minPriceId].toPrice(),
        maxPrice = PriceEntity[maxPriceId].toPrice(),
        clientGender = GenderEntity[clientGenderId].toGender(),
        maxClientWeight = WeightEntity[maxClientWeightId].toWeight(),
        minClientAge = minClientAge,
        maxClientAge = maxClientAge
    )

    override fun toString(): String =
        with(toClientSearch()) {
            "ClientSearch(" +
                    "specialistUser = $specialistUser, " +
                    "serviceType = $serviceType, " +
                    "placeType = $placeType, " +
                    "minPrice = $minPrice, " +
                    "maxPrice = $maxPrice, " +
                    "clientGender = $clientGender, " +
                    "maxClientWeight = $maxClientWeight, " +
                    "minClientAge = $minClientAge, " +
                    "maxClientAge = $maxClientAge" +
                    ")"
        }
}