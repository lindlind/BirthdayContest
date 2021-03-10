package ru.lind.birthday_contest.api.models

import ru.lind.birthday_contest.api.Utils.toPercentageString
import ru.lind.birthday_contest.models.AnswerAttempt
import ru.lind.birthday_contest.models.AnswerAttemptVerdict

data class AnswerResponse(
    val id: Int,
    val time: String,
    val verdict: AnswerAttemptVerdict,
    val currentAnswerMultiplier: String,
    val reward: Int = 0
) {
    constructor(answer: AnswerAttempt, reward: Int = 0): this(
        answer.idInTest,
        answer.createdAt.toString("dd.MM.yyyy HH:mm:ss.SSS"),
        answer.verdict,
        answer.pricePercentage.toPercentageString(),
        reward
    )
}
