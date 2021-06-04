package backend

import backend.data.models.user.*
import backend.data.models.address.*
import backend.routes.*
import backend.routes.address.*
import backend.routes.user.*
import backend.services.address.*
import backend.services.user.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.tomcat.EngineMain
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
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
        // TODO bind services here
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
}

// TODO extension functions
fun DI.MainBuilder.bindFirstNameService() {
    bind<FirstNameService>() with singleton { FirstNameService() }
}

fun DI.MainBuilder.bindLastNameService() {
    bind<LastNameService>() with singleton { LastNameService() }
}

fun DI.MainBuilder.bindGenderService() {
    bind<GenderService>() with singleton { GenderService() }
}

@ExperimentalSerializationApi
fun DI.MainBuilder.bindBirthdayService() {
    bind<BirthdayService>() with singleton { BirthdayService() }
}

fun DI.MainBuilder.bindCountryService() {
    bind<CountryService>() with singleton { CountryService() }
}

fun DI.MainBuilder.bindRegionService() {
    bind<RegionService>() with singleton { RegionService() }
}

fun DI.MainBuilder.bindLocalityTypeService() {
    bind<LocalityTypeService>() with singleton { LocalityTypeService() }
}

fun DI.MainBuilder.bindLocalityService() {
    bind<LocalityService>() with singleton { LocalityService() }
}

fun DI.MainBuilder.bindStreetService() {
    bind<StreetService>() with singleton { StreetService() }
}

fun DI.MainBuilder.bindBuildingService() {
    bind<BuildingService>() with singleton { BuildingService() }
}

fun DI.MainBuilder.bindApartmentService() {
    bind<ApartmentService>() with singleton { ApartmentService() }
}

fun DI.MainBuilder.bindMetroStationService() {
    bind<MetroStationService>() with singleton { MetroStationService() }
}

fun DI.MainBuilder.bindPatronymicService() {
    bind<PatronymicService>() with singleton { PatronymicService() }
}

fun DI.MainBuilder.bindCoordinatesService() {
    bind<CoordinatesService>() with singleton { CoordinatesService() }
}