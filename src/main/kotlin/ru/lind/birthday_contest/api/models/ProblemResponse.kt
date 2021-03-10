package ru.lind.birthday_contest.api.models

import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.models.Problem

data class ProblemResponse(
    val name: String,
    val statement: String,
    val initialReward: String,
    val endpoints: List<EndpointInfo>
) {
    constructor(problem: Problem, endpoints: List<EndpointInfo>) : this(
        problem.name,
        problem.statement,
        problem.price.toRubString(),
        endpoints
    )
}

