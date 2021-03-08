package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object TestsTable : Table("tests") {
    val testId = integer("test_id")
    val problemId = integer("problem_id")
    val testIdInProblem = integer("test_id_in_problem")
    val input = text("input")
    val pricePercentage = integer("test_price_percentage")
    val createdAt = datetime("created_at")
}
