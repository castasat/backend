package backend.data.api.models.place

import kotlinx.serialization.Serializable

// data class for REST API
@Serializable
data class Address(
    val addressId: Long,
    val placeType: PlaceType,
    val coordinates: Coordinates,
    val metroStation: MetroStation,
    val country: Country,
    val region: Region,
    val localityType: LocalityType,
    val locality: Locality,
    val street: Street,
    val building: Building,
    val entrance: Entrance,
    val entranceCode: EntranceCode,
    val apartment: Apartment
)