package backend.data.repositories.place

import backend.data.api.models.place.Coordinates
import backend.data.database.entities.place.CoordinatesEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class CoordinatesRepository {
    fun getAllCoordinates(): Collection<Coordinates> = transaction {
        addLogger(StdOutSqlLogger)
        CoordinatesEntity.all()
            .map { coordinatesEntity: CoordinatesEntity ->
                coordinatesEntity.toCoordinates()
            }
    }

    fun addCoordinates(coordinates: Coordinates) = transaction {
        addLogger(StdOutSqlLogger)
        CoordinatesEntity.new {
            this.longitude = coordinates.longitude
            this.latitude = coordinates.latitude
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteCoordinates(coordinatesId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CoordinatesEntity[coordinatesId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getCoordinates(coordinatesId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        CoordinatesEntity[coordinatesId].toCoordinates()
    }
}