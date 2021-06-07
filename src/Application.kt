package backend

import backend.data.api.routes.*
import backend.data.database.tables.*
import backend.data.repositories.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.tomcat.EngineMain
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import org.slf4j.LoggerFactory

const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"

fun main(args: Array<String>) = EngineMain.main(args)

@ExperimentalSerializationApi
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    initDB()

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) { json() }

    di {
        bindFirstNameRepository()
        bindLastNameRepository()
        bindGenderRepository()
        bindBirthdayRepository()
        bindCountryRepository()
        bindRegionRepository()
        bindLocalityTypeRepository()
        bindLocalityRepository()
        bindStreetRepository()
        bindBuildingRepository()
        bindApartmentRepository()
        bindMetroStationRepository()
        bindPatronymicRepository()
        bindCoordinatesRepository()
        bindEntranceRepository()
        bindEntranceCodeRepository()
        bindServiceTypeRepository()
        bindPriceRepository()
        // TODO bind data.repositories here
    }

    routing {
        rootRoute()
        firstNameRoute()
        lastNameRoute()
        genderRoute()
        birthdayRoute()
        countryRoute()
        regionRoute()
        localityTypeRoute()
        localityRoute()
        streetRoute()
        buildingRoute()
        apartmentRoute()
        metroStationRoute()
        patronymicRoute()
        coordinatesRoute()
        entranceRoute()
        entranceCodeRoute()
        serviceTypeRoute()
        priceRoute()
        // TODO add routing here
    }
}

fun Application.initDB() {
    val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
    val dbConfig = HikariConfig(configPath)
    val dataSource = HikariDataSource(dbConfig)
    Database.connect(dataSource)

    createTables()

    LoggerFactory.getLogger(Application::class.simpleName).info("Initialized Database")
}

private fun createTables() = transaction {
    SchemaUtils.create(FirstNamesTable)
    SchemaUtils.create(LastNamesTable)
    SchemaUtils.create(GendersTable)
    SchemaUtils.create(BirthdaysTable)
    SchemaUtils.create(CountriesTable)
    SchemaUtils.create(RegionsTable)
    SchemaUtils.create(LocalityTypesTable)
    SchemaUtils.create(LocalitiesTable)
    SchemaUtils.create(StreetsTable)
    SchemaUtils.create(BuildingsTable)
    SchemaUtils.create(ApartmentsTable)
    SchemaUtils.create(MetroStationsTable)
    SchemaUtils.create(PatronymicsTable)
    SchemaUtils.create(CoordinatesTable)
    SchemaUtils.create(EntrancesTable)
    SchemaUtils.create(EntranceCodesTable)
    SchemaUtils.create(ServiceTypesTable)
    SchemaUtils.create(PricesTable)
    // TODO create tables here
}

fun DI.MainBuilder.bindFirstNameRepository() {
    bind<FirstNameRepository>() with singleton { FirstNameRepository() }
}

fun DI.MainBuilder.bindLastNameRepository() {
    bind<LastNameRepository>() with singleton { LastNameRepository() }
}

fun DI.MainBuilder.bindGenderRepository() {
    bind<GenderRepository>() with singleton { GenderRepository() }
}

@ExperimentalSerializationApi
fun DI.MainBuilder.bindBirthdayRepository() {
    bind<BirthdayRepository>() with singleton { BirthdayRepository() }
}

fun DI.MainBuilder.bindCountryRepository() {
    bind<CountryRepository>() with singleton { CountryRepository() }
}

fun DI.MainBuilder.bindRegionRepository() {
    bind<RegionRepository>() with singleton { RegionRepository() }
}

fun DI.MainBuilder.bindLocalityTypeRepository() {
    bind<LocalityTypeRepository>() with singleton { LocalityTypeRepository() }
}

fun DI.MainBuilder.bindLocalityRepository() {
    bind<LocalityRepository>() with singleton { LocalityRepository() }
}

fun DI.MainBuilder.bindStreetRepository() {
    bind<StreetRepository>() with singleton { StreetRepository() }
}

fun DI.MainBuilder.bindBuildingRepository() {
    bind<BuildingRepository>() with singleton { BuildingRepository() }
}

fun DI.MainBuilder.bindApartmentRepository() {
    bind<ApartmentRepository>() with singleton { ApartmentRepository() }
}

fun DI.MainBuilder.bindMetroStationRepository() {
    bind<MetroStationRepository>() with singleton { MetroStationRepository() }
}

fun DI.MainBuilder.bindPatronymicRepository() {
    bind<PatronymicRepository>() with singleton { PatronymicRepository() }
}

fun DI.MainBuilder.bindCoordinatesRepository() {
    bind<CoordinatesRepository>() with singleton { CoordinatesRepository() }
}

fun DI.MainBuilder.bindEntranceRepository() {
    bind<EntranceRepository>() with singleton { EntranceRepository() }
}

fun DI.MainBuilder.bindEntranceCodeRepository() {
    bind<EntranceCodeRepository>() with singleton { EntranceCodeRepository() }
}

fun DI.MainBuilder.bindServiceTypeRepository() {
    bind<ServiceTypeRepository>() with singleton { ServiceTypeRepository() }
}

fun DI.MainBuilder.bindPriceRepository() {
    bind<PriceRepository>() with singleton { PriceRepository() }
}
// TODO extension functions