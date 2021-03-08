package ru.lind.birthday_contest.entities

import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbTest

data class Test(
    val id: Int,
    val problemId: Int,
    val idInProblem: Int,
    val input: String,
    val pricePercentage: Int,
    val createdAt: DateTime
) {
    constructor(dbTest: DbTest) : this(
        dbTest.testId,
        dbTest.problemId,
        dbTest.testIdInProblem,
        dbTest.input,
        dbTest.pricePercentage,
        dbTest.createdAt
    )
}
