package ru.lind.birthday_contest.api.models

import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.models.Problem

data class ProblemRewardRules(
    val name: String,
    val initialReward: String,
    val testMultiplierRules: String,
    val answerAttemptMultiplierRules: String
)
