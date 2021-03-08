package ru.lind.birthday_contest.entities

import org.joda.time.DateTime

interface Test {
    val id: Int
    val problemId: Int
    val input: String
    val pricePercentage: Int
    val createdAt: DateTime
}