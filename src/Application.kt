package backend

import backend.data.models.BirthdaysTable
import backend.data.models.FirstNamesTable
import backend.data.models.GendersTable
import backend.data.models.LastNamesTable
import backend.routes.*
import backend.services.BirthdayService
import backend.services.FirstNameService
import backend.services.GenderService
import backend.services.LastNameService
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
    }

    routing {
        // TODO add routing here
        rootRoute()
        firstNameRoute()
        lastNameRoute()
        genderRoute()
        birthdayRoute()
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

fun DI.MainBuilder.bindBirthdayService() {
    bind<BirthdayService>() with singleton { BirthdayService() }
}