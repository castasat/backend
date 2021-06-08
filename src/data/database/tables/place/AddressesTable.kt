package backend.data.database.tables.place

import backend.data.database.CommonConstants
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object AddressesTable : LongIdTable() {
    private const val PLACE_TYPE_ID = "PLACE_TYPE_ID"
    private const val COORDINATES_ID = "COORDINATES_ID"
    private const val METRO_STATION_ID = "METRO_STATION_ID"
    private const val COUNTRY_ID = "COUNTRY_ID"
    private const val REGION_ID = "REGION_ID"
    private const val LOCALITY_TYPE_ID = "LOCALITY_TYPE_ID"
    private const val LOCALITY_ID = "LOCALITY_ID"
    private const val STREET_ID = "STREET_ID"
    private const val BUILDING_ID = "BUILDING_ID"
    private const val ENTRANCE_ID = "ENTRANCE_ID"
    private const val ENTRANCE_CODE_ID = "ENTRANCE_CODE_ID"
    private const val APARTMENT_ID = "APARTMENT_ID"

    val placeTypeId: Column<Long> = long(name = PLACE_TYPE_ID)
    val coordinatesId: Column<Long> = long(name = COORDINATES_ID)
    val metroStationId: Column<Long> = long(name = METRO_STATION_ID)
    val countryId: Column<Long> = long(name = COUNTRY_ID)
    val regionId: Column<Long> = long(name = REGION_ID)
    val localityTypeId: Column<Long> = long(name = LOCALITY_TYPE_ID)
    val localityId: Column<Long> = long(name = LOCALITY_ID)
    val streetId: Column<Long> = long(name = STREET_ID)
    val buildingId: Column<Long> = long(name = BUILDING_ID)
    val entranceId: Column<Long> = long(name = ENTRANCE_ID)
    val entranceCodeId: Column<Long> = long(name = ENTRANCE_CODE_ID)
    val apartmentId: Column<Long> = long(name = APARTMENT_ID)
}