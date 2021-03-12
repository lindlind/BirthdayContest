package ru.lind.birthday_contest.database.entities

import org.joda.time.DateTime

data class DbGameOfLifeTest(
    val testId: Int,
    val input: String,
    val answer: Int,
    val usedAt: DateTime?
)

