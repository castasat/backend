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
        // TODO bind data.repositories here
        bindFirstNameService()
        bindLastNameService()
        bindGenderService()
        bindBirthdayService()
        bindCountryService()
        bindRegionService()
        bindLocalityTypeService()
        bindLocalityService()
        bindStreetService()
        bindBuildingService()
        bindApartmentService()
        bindMetroStationService()
        bindPatronymicService()
        bindCoordinatesService()
        bindEntranceService()
        bindEntranceCodeService()
    }

    routing {
        // TODO add routing here
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
    // TODO create tables here
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
}

// TODO extension functions
fun DI.MainBuilder.bindFirstNameService() {
    bind<FirstNameRepository>() with singleton { FirstNameRepository() }
}

fun DI.MainBuilder.bindLastNameService() {
    bind<LastNameRepository>() with singleton { LastNameRepository() }
}

fun DI.MainBuilder.bindGenderService() {
    bind<GenderRepository>() with singleton { GenderRepository() }
}

@ExperimentalSerializationApi
fun DI.MainBuilder.bindBirthdayService() {
    bind<BirthdayRepository>() with singleton { BirthdayRepository() }
}

fun DI.MainBuilder.bindCountryService() {
    bind<CountryRepository>() with singleton { CountryRepository() }
}

fun DI.MainBuilder.bindRegionService() {
    bind<RegionRepository>() with singleton { RegionRepository() }
}

fun DI.MainBuilder.bindLocalityTypeService() {
    bind<LocalityTypeRepository>() with singleton { LocalityTypeRepository() }
}

fun DI.MainBuilder.bindLocalityService() {
    bind<LocalityRepository>() with singleton { LocalityRepository() }
}

fun DI.MainBuilder.bindStreetService() {
    bind<StreetRepository>() with singleton { StreetRepository() }
}

fun DI.MainBuilder.bindBuildingService() {
    bind<BuildingRepository>() with singleton { BuildingRepository() }
}

fun DI.MainBuilder.bindApartmentService() {
    bind<ApartmentRepository>() with singleton { ApartmentRepository() }
}

fun DI.MainBuilder.bindMetroStationService() {
    bind<MetroStationRepository>() with singleton { MetroStationRepository() }
}

fun DI.MainBuilder.bindPatronymicService() {
    bind<PatronymicRepository>() with singleton { PatronymicRepository() }
}

fun DI.MainBuilder.bindCoordinatesService() {
    bind<CoordinatesRepository>() with singleton { CoordinatesRepository() }
}

fun DI.MainBuilder.bindEntranceService() {
    bind<EntranceRepository>() with singleton { EntranceRepository() }
}

fun DI.MainBuilder.bindEntranceCodeService() {
    bind<EntranceCodeRepository>() with singleton { EntranceCodeRepository() }
}