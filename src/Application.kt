package backend

import backend.data.models.*
import backend.routes.*
import backend.services.*
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
        // TODO bind services here
        bindFirstNameService()
        bindLastNameService()
        bindGenderService()
        bindBirthdayService()
        bindCountryService()
        bindRegionService()
        bindLocalityTypeService()
        bindLocalityService()
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