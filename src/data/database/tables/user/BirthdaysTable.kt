package backend.data.database.tables.user

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.date
import java.time.LocalDate

// JDBC Expose Table with autoincrement long primary key
object BirthdaysTable : LongIdTable() {
    private const val BIRTHDAY = "BIRTHDAY"
    val birthday: Column<LocalDate> =
        date(
            name = BIRTHDAY
        )
}