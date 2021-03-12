package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object LabyrinthWithTeleportsTestsTable : Table("teleports_tests") {
    val testId = integer("teleports_test_id")
    val input = text("teleports_test_input")
    val usedAt = datetime("teleports_tests_used_at").nullable()
}
