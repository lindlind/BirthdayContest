package ru.lind.birthday_contest.problems.graph_nim_problem

import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.Problem
import ru.lind.birthday_contest.problems.UnfairBinarySearchProblem
import kotlin.math.min
import kotlin.random.Random

class GraphNimProblemEasy private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : GraphNimProblem() {

    private val p = listOf(4, 1, 2, 8, 1, 4, 7, 2, 1, 8, 2, 7)

    override fun getNimSum(vararg nums: Int) = nums.fold(0) { acc, n ->
        acc xor p[(n % 12 + 12) % 12]
    }

    companion object {

        private const val PROBLEM_ID: Int = 2

        fun init(): GraphNimProblemEasy {
            val dbProblem = ProblemQueries.get(PROBLEM_ID)
            return dbProblem?.let {
                GraphNimProblemEasy(
                    dbProblem.problemId,
                    dbProblem.name,
                    dbProblem.statement,
                    dbProblem.price
                )
            } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
        }

    }

}
