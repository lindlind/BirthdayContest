package ru.lind.birthday_contest.api.models

import ru.lind.birthday_contest.api.Utils.toPercentageString
import ru.lind.birthday_contest.models.Test

data class TestResponse(
    val id: Int,
    val testMultiplier: String,
    val input: String
) {
    constructor(test: Test): this(
        test.idInProblem,
        test.pricePercentage.toPercentageString(),
        test.input
    )
}
