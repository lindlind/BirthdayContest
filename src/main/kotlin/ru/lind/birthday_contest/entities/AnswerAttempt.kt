package ru.lind.birthday_contest.entities

import org.joda.time.DateTime

interface AnswerAttempt {
    val id: Int
    val testId: Int
    val answer: String
    val pricePercentage: Int
    val createdAt: DateTime
    val verdict: AnswerAttemptVerdict
}