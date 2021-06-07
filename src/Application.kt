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
    di { bindRepositories() }
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
        currencyRoute()
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
    with(SchemaUtils) {
        create(FirstNamesTable)
        create(LastNamesTable)
        create(GendersTable)
        create(BirthdaysTable)
        create(CountriesTable)
        create(RegionsTable)
        create(LocalityTypesTable)
        create(LocalitiesTable)
        create(StreetsTable)
        create(BuildingsTable)
        create(ApartmentsTable)
        create(MetroStationsTable)
        create(PatronymicsTable)
        create(CoordinatesTable)
        create(EntrancesTable)
        create(EntranceCodesTable)
        create(ServiceTypesTable)
        create(PricesTable)
        create(CurrenciesTable)
        // TODO create tables here
    }
}

@ExperimentalSerializationApi
fun DI.MainBuilder.bindRepositories() {
    bind<FirstNameRepository>() with singleton { FirstNameRepository() }
    bind<LastNameRepository>() with singleton { LastNameRepository() }
    bind<GenderRepository>() with singleton { GenderRepository() }
    bind<BirthdayRepository>() with singleton { BirthdayRepository() }
    bind<CountryRepository>() with singleton { CountryRepository() }
    bind<RegionRepository>() with singleton { RegionRepository() }
    bind<LocalityTypeRepository>() with singleton { LocalityTypeRepository() }
    bind<LocalityRepository>() with singleton { LocalityRepository() }
    bind<StreetRepository>() with singleton { StreetRepository() }
    bind<BuildingRepository>() with singleton { BuildingRepository() }
    bind<ApartmentRepository>() with singleton { ApartmentRepository() }
    bind<MetroStationRepository>() with singleton { MetroStationRepository() }
    bind<PatronymicRepository>() with singleton { PatronymicRepository() }
    bind<CoordinatesRepository>() with singleton { CoordinatesRepository() }
    bind<EntranceRepository>() with singleton { EntranceRepository() }
    bind<EntranceCodeRepository>() with singleton { EntranceCodeRepository() }
    bind<ServiceTypeRepository>() with singleton { ServiceTypeRepository() }
    bind<PriceRepository>() with singleton { PriceRepository() }
    bind<CurrencyRepository>() with singleton { CurrencyRepository() }
    // TODO extension functions
}
