package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.database.queries.GameOfLifeTestQueries
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import java.lang.RuntimeException
import kotlin.math.max

class GameOfLifeProblem private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        val test = GameOfLifeTestQueries.getNotUsed() ?: throw RuntimeException("Out of tests!")
        GameOfLifeTestQueries.setUsed(test.testId)
        return test.input
    }

    override fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?> {
        val test = GameOfLifeTestQueries.getLastUsed() ?: throw RuntimeException("Cant find used test!")
        if (test.input != input) { throw RuntimeException("Wrong input!") }

        return if (test.answer == answer.toIntOrNull()) {
            AnswerAttemptVerdict.ACCEPTED to null
        } else {
            AnswerAttemptVerdict.REJECTED to null
        }
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        return max(1, 100 - 2 * (testIdInProblem - 1))
    }

    override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        return when(answerIdInTest) {
            1 -> 100
            2 -> 98
            3 -> 10
            else -> 1
        }
    }

    val testMultiplierRules = "(100 - 2 * (testId - 1))%, testId = 1.."
    val answerMultiplierRules = "attemptId = 1: 100%, attemptId = 2: 98%, attemptId = 3: 10%"

    companion object {

        private const val PROBLEM_ID: Int = 4

        fun init(): GameOfLifeProblem {
            val dbProblem = ProblemQueries.get(PROBLEM_ID)
            return dbProblem?.let {
                GameOfLifeProblem(
                    dbProblem.problemId,
                    dbProblem.name,
                    dbProblem.statement,
                    dbProblem.price
                )
            } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
        }

    }

}