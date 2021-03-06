package ru.lind.birthday_contest.api.models

import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.models.Problem

data class ProblemStatsResponse(
    val name: String,
    val bestAttemptReward: String,
    val initialReward: String,
    val currentTestId: Int,
    val currentTestMultiplier: String,
    val lastAnswerAttemptId: Int,
    val lastAnswerAttemptMultiplier: String
)
