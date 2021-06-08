package backend.data.repositories.place

import backend.data.api.models.place.PlaceType
import backend.data.database.entities.place.PlaceTypeEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class PlaceTypeRepository {
    fun getAllPlaceTypes(): Collection<PlaceType> = transaction {
        addLogger(StdOutSqlLogger)
        PlaceTypeEntity.all()
            .map { placeTypeEntity: PlaceTypeEntity ->
                placeTypeEntity.toPlaceType()
            }
    }

    fun addPlaceType(placeType: PlaceType) = transaction {
        addLogger(StdOutSqlLogger)
        PlaceTypeEntity.new {
            this.placeType = placeType.placeType
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deletePlaceType(placeTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PlaceTypeEntity[placeTypeId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getPlaceType(placeTypeId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        PlaceTypeEntity[placeTypeId].toPlaceType()
    }
}