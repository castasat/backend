package backend.data.database.entities.place

import backend.data.api.models.place.Address
import backend.data.database.tables.place.AddressesTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class AddressEntity(private var addressId: EntityID<Long>) : LongEntity(id = addressId) {
    companion object : LongEntityClass<AddressEntity>(AddressesTable)

    var placeTypeId: Long by AddressesTable.placeTypeId
    var coordinatesId: Long by AddressesTable.coordinatesId
    var metroStationId: Long by AddressesTable.metroStationId
    var countryId: Long by AddressesTable.countryId
    var regionId: Long by AddressesTable.regionId
    var localityTypeId: Long by AddressesTable.localityTypeId
    var localityId: Long by AddressesTable.localityId
    var streetId: Long by AddressesTable.streetId
    var buildingId: Long by AddressesTable.buildingId
    var entranceId: Long by AddressesTable.entranceId
    var entranceCodeId: Long by AddressesTable.entranceCodeId
    var apartmentId: Long by AddressesTable.apartmentId

    fun toAddress() = Address(
        addressId = addressId.value,
        placeType = PlaceTypeEntity[placeTypeId].toPlaceType(),
        coordinates = CoordinatesEntity[coordinatesId].toCoordinates(),
        metroStation = MetroStationEntity[metroStationId].toMetroStation(),
        country = CountryEntity[countryId].toCountry(),
        region = RegionEntity[regionId].toRegion(),
        localityType = LocalityTypeEntity[localityTypeId].toLocalityType(),
        locality = LocalityEntity[localityId].toLocality(),
        street = StreetEntity[streetId].toStreet(),
        building = BuildingEntity[buildingId].toBuilding(),
        entrance = EntranceEntity[entranceId].toEntrance(),
        entranceCode = EntranceCodeEntity[entranceCodeId].toEntranceCode(),
        apartment = ApartmentEntity[apartmentId].toApartment()
    )

    override fun toString(): String =
        with(toAddress()) {
            "Address(" +
                    "placeType = $placeType, " +
                    "coordinates = $coordinates, " +
                    "metroStation = $metroStation, " +
                    "country = $country, " +
                    "region = $region, " +
                    "localityType = $localityType, " +
                    "locality = $locality, " +
                    "street = $street, " +
                    "building = $building, " +
                    "entrance = $entranceCode, " +
                    "apartment = $apartment"
                    ")"
        }
}