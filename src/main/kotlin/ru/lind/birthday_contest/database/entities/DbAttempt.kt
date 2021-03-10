package ru.lind.birthday_contest.database.entities

import org.joda.time.DateTime
import ru.lind.birthday_contest.models.AnswerAttemptVerdict

data class DbAttempt(
    val attemptId: Int,
    val testId: Int,
    val attemptIdInTest: Int,
    val answer: String,
    val pricePercentage: Int,
    val createdAt: DateTime,
    val verdict: AnswerAttemptVerdict
)
