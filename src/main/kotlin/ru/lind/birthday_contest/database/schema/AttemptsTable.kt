package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object AttemptsTable : Table("attempts") {
    val attemptId = integer("attempt_id")
    val testId = integer("test_id")
    val attemptIdInTest = integer("attempt_id_in_test")
    val answer = text("answer")
    val pricePercentage = integer("attempt_price_percentage")
    val verdict = enumAttemptVerdict("verdict")
    val createdAt = datetime("created_at")
}
