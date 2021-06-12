package backend

import backend.data.api.routes.*
import backend.data.api.routes.payment.paymentRoute
import backend.data.api.routes.price.currencyRoute
import backend.data.api.routes.payment.paymentTypeRoute
import backend.data.api.routes.place.*
import backend.data.api.routes.price.priceRoute
import backend.data.api.routes.search.offerSearchRoute
import backend.data.api.routes.service.offerRoute
import backend.data.api.routes.service.orderRoute
import backend.data.api.routes.service.serviceRoute
import backend.data.api.routes.service.serviceTypeRoute
import backend.data.api.routes.user.*
import backend.data.database.tables.price.CurrenciesTable
import backend.data.database.tables.payment.PaymentTypesTable
import backend.data.database.tables.payment.PaymentsTable
import backend.data.database.tables.place.*
import backend.data.database.tables.price.PricesTable
import backend.data.database.tables.search.OfferSearchesTable
import backend.data.database.tables.service.OffersTable
import backend.data.database.tables.service.OrdersTable
import backend.data.database.tables.service.ServiceTypesTable
import backend.data.database.tables.service.ServicesTable
import backend.data.database.tables.user.*
import backend.data.repositories.payment.PaymentRepository
import backend.data.repositories.price.CurrencyRepository
import backend.data.repositories.payment.PaymentTypeRepository
import backend.data.repositories.place.*
import backend.data.repositories.price.PriceRepository
import backend.data.repositories.search.OfferSearchRepository
import backend.data.repositories.service.OfferRepository
import backend.data.repositories.service.OrderRepository
import backend.data.repositories.service.ServiceRepository
import backend.data.repositories.service.ServiceTypeRepository
import backend.data.repositories.user.*
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

@Suppress("UNUSED_PARAMETER")
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
        paymentTypeRoute()
        placeTypeRoute()
        addressRoute()
        userRoute()
        serviceRoute()
        offerRoute()
        orderRoute()
        paymentRoute()
        offerSearchRoute()
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
        create(PaymentTypesTable)
        create(PlaceTypesTable)
        create(AddressesTable)
        create(UsersTable)
        create(ServicesTable)
        create(OffersTable)
        create(OrdersTable)
        create(PaymentsTable)
        create(OfferSearchesTable)
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
    bind<PaymentTypeRepository>() with singleton { PaymentTypeRepository() }
    bind<PlaceTypeRepository>() with singleton { PlaceTypeRepository() }
    bind<AddressRepository>() with singleton { AddressRepository() }
    bind<UserRepository>() with singleton { UserRepository() }
    bind<ServiceRepository>() with singleton { ServiceRepository() }
    bind<OfferRepository>() with singleton { OfferRepository() }
    bind<OrderRepository>() with singleton { OrderRepository() }
    bind<PaymentRepository>() with singleton { PaymentRepository() }
    bind<OfferSearchRepository>() with singleton { OfferSearchRepository() }
    // TODO extension functions
}
