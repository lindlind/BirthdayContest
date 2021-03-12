package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object GameOfLifeTestsTable : Table("game_of_life_tests") {
    val testId = integer("gol_test_id")
    val input = text("gol_test_input")
    val answer = integer("gol_test_answer")
    val usedAt = datetime("gol_test_used_at").nullable()
}
