package backend.services

import backend.data.models.Locality
import backend.data.models.LocalityEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class LocalityService {
    fun getAllLocalities(): Collection<Locality> = transaction {
        addLogger(StdOutSqlLogger)
        LocalityEntity.all()
            .map { localityEntity: LocalityEntity ->
                localityEntity.toLocality()
            }
    }

    fun addLocality(locality: Locality) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityEntity.new {
            this.locality = locality.locality
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteLocality(localityId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityEntity[localityId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getLocality(localityId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        LocalityEntity[localityId].toLocality()
    }
}