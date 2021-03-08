package ru.lind.birthday_contest.entities

import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbAttempt

data class AnswerAttempt(
    val id: Int,
    val testId: Int,
    val idInTest: Int,
    val answer: String,
    val pricePercentage: Int,
    val createdAt: DateTime,
    var verdict: AnswerAttemptVerdict
) {
    constructor(dbAttempt: DbAttempt) : this(
        dbAttempt.attemptId,
        dbAttempt.testId,
        dbAttempt.attemptIdInTest,
        dbAttempt.answer,
        dbAttempt.pricePercentage,
        dbAttempt.createdAt,
        dbAttempt.verdict
    )
}
