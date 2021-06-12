package backend.data.repositories.user

import backend.data.api.models.user.Weight
import backend.data.database.entities.user.WeightEntity
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.jvm.Throws

class WeightRepository {
    fun getAllWeights(): Collection<Weight> = transaction {
        addLogger(StdOutSqlLogger)
        WeightEntity.all()
            .map { weightEntity: WeightEntity ->
                weightEntity.toWeight()
            }
    }

    fun addWeight(weight: Weight) = transaction {
        addLogger(StdOutSqlLogger)
        WeightEntity.new {
            this.weight = weight.weight
        }
    }

    @Throws(EntityNotFoundException::class)
    fun deleteWeight(weightId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        WeightEntity[weightId].delete()
    }

    @Throws(EntityNotFoundException::class)
    fun getWeight(weightId: Long) = transaction {
        addLogger(StdOutSqlLogger)
        WeightEntity[weightId].toWeight()
    }
}