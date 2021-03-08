package ru.lind.birthday_contest.database.entities

import org.joda.time.DateTime

data class DbTest(
    val testId: Int,
    val problemId: Int,
    val testIdInProblem: Int,
    val input: String,
    val pricePercentage: Int,
    val createdAt: DateTime
)
