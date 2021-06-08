package backend.data.repositories.place

import backend.data.api.models.place.Address
import backend.data.database.entities.place.AddressEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class AddressRepository {
    fun getAllAddresses(): Collection<Address> = transaction {
        addLogger(StdOutSqlLogger)
        AddressEntity.all()
            .map { addressEntity: AddressEntity ->
                addressEntity.toAddress()
            }
    }

    fun addAddress(address: Address) = transaction {
        addLogger(StdOutSqlLogger)
        with(address) {
            AddressEntity.new {
                this.placeTypeId = placeType.placeTypeId
                this.coordinatesId = coordinates.coordinatesId
                this.metroStationId = metroStation.metroStationId
                this.countryId = country.countryId
                this.regionId = region.regionId
                this.localityTypeId = localityType.localityTypeId
                this.localityId = locality.localityId
                this.streetId = street.streetId
                this.buildingId = building.buildingId
                this.entranceId = entrance.entranceId
                this.entranceCodeId = entranceCode.entranceCodeId
                this.apartmentId = apartment.apartmentId
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteAddress(addressId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        AddressEntity[addressId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getAddress(addressId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        AddressEntity[addressId].toAddress()
    }
}