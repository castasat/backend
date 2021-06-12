package backend.data.database.entities.user

import backend.data.api.models.user.Weight
import backend.data.database.tables.user.WeightsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// JDBC Expose DAO Entity representing Row in a Table
class WeightEntity(private var weightId: EntityID<Long>) : LongEntity(id = weightId) {
    companion object : LongEntityClass<WeightEntity>(WeightsTable)
    var weight: String by WeightsTable.weight

    override fun toString(): String =
        "Weight(" +
                "weight = $weight" +
                ")"

    fun toWeight() =
        Weight(
            weightId = weightId.value,
            weight = weight
        )
}