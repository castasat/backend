package backend.data.database.tables.user

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

// JDBC Expose Table with autoincrement long primary key
object UsersTable : LongIdTable() {
    private const val FIRST_NAME_ID = "FIRST_NAME_ID"
    private const val LAST_NAME_ID = "LAST_NAME_ID"
    private const val PATRONYMIC_ID = "PATRONYMIC_ID"
    private const val GENDER_ID = "GENDER_ID"
    private const val WEIGHT_ID = "WEIGHT_ID"
    private const val BIRTHDAY_ID = "BIRTHDAY_ID"
    private const val ADDRESS_ID = "ADDRESS_ID"

    val firstNameId: Column<Long> = long(name = FIRST_NAME_ID)
    val lastNameId: Column<Long> = long(name = LAST_NAME_ID)
    val patronymicId: Column<Long> = long(name = PATRONYMIC_ID)
    val genderId: Column<Long> = long(name = GENDER_ID)
    val weightId: Column<Long> = long(name = WEIGHT_ID)
    val birthdayId: Column<Long> = long(name = BIRTHDAY_ID)
    val addressId: Column<Long> = long(name = ADDRESS_ID)
}