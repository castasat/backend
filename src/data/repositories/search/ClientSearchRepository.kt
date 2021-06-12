package backend.data.repositories.search

import backend.data.api.models.search.ClientSearch
import backend.data.database.entities.search.ClientSearchEntity
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

@ExperimentalSerializationApi
class ClientSearchRepository {

    fun getAllClientSearches(): Collection<ClientSearch> = transaction {
        addLogger(StdOutSqlLogger)
        ClientSearchEntity.all()
            .map { clientSearchEntity: ClientSearchEntity ->
                clientSearchEntity.toClientSearch()
            }
    }

    fun addClientSearch(clientSearch: ClientSearch) = transaction {
        addLogger(StdOutSqlLogger)
        with(clientSearch) {
            ClientSearchEntity.new {
                this.specialistUserId = specialistUser.userId
                this.serviceTypeId = serviceType.serviceTypeId
                this.placeTypeId = placeType.placeTypeId
                this.minPriceId = minPrice.priceId
                this.maxPriceId = maxPrice.priceId
                this.clientGenderId = clientGender.genderId
                this.maxClientWeightId = maxClientWeight.weightId
                this.minClientAge = clientSearch.minClientAge
                this.maxClientAge = clientSearch.maxClientAge
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteClientSearch(clientSearchId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ClientSearchEntity[clientSearchId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getClientSearch(clientSearchId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        ClientSearchEntity[clientSearchId].toClientSearch()
    }
}