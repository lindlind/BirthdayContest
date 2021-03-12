package ru.lind.birthday_contest.problems.graph_nim_problem

import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.Problem
import kotlin.random.Random

class GraphNimProblemHard private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : GraphNimProblem() {

    private val p = listOf(4, 1, 2, 8, 1, 4, 7, 2, 1, 8, 2, 7)
    private val c = listOf(13, 22, 11, 25, 19, 22, 14, 21, 22, 19, 11, 21)
    private val t = listOf(15, 21, 9, 19, 18, 4, 21, 22, 1, 16, 21, 25)

    override val minAns = 5

    override fun getNimSum(vararg nums: Int) = when (nums.size) {
        2 -> t[(nums[0] % 12 + 12) % 12] xor p[(nums[1] % 12 + 12) % 12]
        3 -> nums.fold(0) { acc, n -> acc xor t[(n % 12 + 12) % 12] }
        else -> throw IllegalStateException("Fail in nim sum")
    }

    companion object {

        private const val PROBLEM_ID: Int = 3

        fun init(): GraphNimProblemHard {
            val dbProblem = ProblemQueries.get(PROBLEM_ID)
            return dbProblem?.let {
                GraphNimProblemHard(
                    dbProblem.problemId,
                    dbProblem.name,
                    dbProblem.statement,
                    dbProblem.price
                )
            } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
        }

    }

}
