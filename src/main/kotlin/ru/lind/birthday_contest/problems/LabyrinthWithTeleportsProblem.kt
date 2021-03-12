package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.database.queries.GameOfLifeTestQueries
import ru.lind.birthday_contest.database.queries.LabyrinthWithTeleportsQueries
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import java.lang.RuntimeException
import kotlin.math.max

class LabyrinthWithTeleportsProblem private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        val test = LabyrinthWithTeleportsQueries.getNotUsed() ?: throw RuntimeException("Out of tests!")
        LabyrinthWithTeleportsQueries.setUsed(test.testId)
        return test.input
    }

    override fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?> {
        val (labyrinthAsInt, cell) = input.split('|').map{ it.toInt() }
        val isTeleport = { x: Int, y: Int ->
            when {
                x < 0 || y < 0 -> true
                x > 4 || y > 4 -> true
                else -> (labyrinthAsInt shr (x * 5 + y)) % 2 == 1
            }
        }

        var (x, y) = 0 to 0
        answer.forEach { direction ->
            when (direction) {
                'U' -> x -= 1
                'D' -> x += 1
                'L' -> y -= 1
                'R' -> y += 1
                else -> throw IllegalArgumentException("Error while parsing answer")
            }
            if (isTeleport(x,y)) { x = 0; y = 0 }
            if (x * 5 + y == cell) {
                return AnswerAttemptVerdict.ACCEPTED to null
            }
        }

        return AnswerAttemptVerdict.REJECTED to "You was at ($x, $y). Next time you'll start at (0, 0)."
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        return max(1, 100 - 2 * (testIdInProblem - 1))
    }

    override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        return max(1, 100 - 6 * (answerIdInTest - 1))
    }

    val testMultiplierRules = "(100 - 2 * (testId - 1))%, testId = 1.."
    val answerMultiplierRules = "(100 - 6 * (attemptId - 1))%, attemptId = 1.."

    companion object {

        private const val PROBLEM_ID: Int = 5

        fun init(): LabyrinthWithTeleportsProblem {
            val dbProblem = ProblemQueries.get(PROBLEM_ID)
            return dbProblem?.let {
                LabyrinthWithTeleportsProblem(
                    dbProblem.problemId,
                    dbProblem.name,
                    dbProblem.statement,
                    dbProblem.price
                )
            } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
        }

    }

}