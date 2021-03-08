package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.lind.birthday_contest.entities.AnswerAttemptVerdict

interface DbEnum<T> {
    fun get(value: String) : T
}

private class PostgresEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}

private fun <T : Enum<T>> Table.enum(name: String, sqlType: String, innerType: DbEnum<T>) = customEnumeration(
    name, sqlType,
    { innerType.get(it as String) },
    { PostgresEnum(sqlType, it) }
)

fun Table.enumAttemptVerdict(name: String) =
    this.enum(name, "attemptVerdictType", AnswerAttemptVerdict)
