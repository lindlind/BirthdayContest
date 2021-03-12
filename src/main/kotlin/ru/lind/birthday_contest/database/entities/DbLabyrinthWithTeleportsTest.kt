package ru.lind.birthday_contest.database.entities

import org.joda.time.DateTime

data class DbLabyrinthWithTeleportsTest(
    val testId: Int,
    val input: String,
    val usedAt: DateTime?
)

