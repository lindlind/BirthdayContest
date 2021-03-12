package ru.lind.birthday_contest.problems

import org.joda.time.DateTime
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import kotlin.math.max

class UnfairBinarySearchProblem private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        val now = DateTime.now()
        val number = (1 + now.millisOfSecond) * (1 + now.minuteOfHour) * (1 + now.dayOfWeek)
        return number.toString()
    }

    override fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?> {
        val expected = input.toInt()
        val actual = answer.toIntOrNull()
        if (expected == actual) {
            return AnswerAttemptVerdict.ACCEPTED to null
        }
        return AnswerAttemptVerdict.REJECTED to actual?.let {
            if (expected > actual) { "Больше" } else { "Меньше" }
        }
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        return max(1, 100 - 2 * (testIdInProblem - 1))
    }

    override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        return when(answerIdInTest) {
            in 1..4 -> 100
            5 -> 95
            6 -> 85
            7 -> 50
            8 -> 10
            else -> 1
        }
    }

    val testMultiplierRules = "(100 - 2 * (testId - 1))%, testId = 1.."
    val answerMultiplierRules = "attemptId = 1..4: 100%, attemptId = 5: 95%, attemptId = 6: 85%, attemptId = 7: 50%, attemptId = 8: 10%"

    companion object {

        private const val PROBLEM_ID: Int = 1

        fun init(): UnfairBinarySearchProblem {
            val dbProblem = ProblemQueries.get(PROBLEM_ID)
            return dbProblem?.let {
                UnfairBinarySearchProblem(
                    dbProblem.problemId,
                    dbProblem.name,
                    dbProblem.statement,
                    dbProblem.price
                )
            } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
        }

    }

}