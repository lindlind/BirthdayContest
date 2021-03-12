package ru.lind.birthday_contest.problems

import org.joda.time.DateTime
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import kotlin.math.max
import kotlin.random.Random

class UnfairBinarySearchProblem private constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        val now = DateTime.now()
        val number = (1 + now.millisOfSecond) * (1 + now.minuteOfHour) * now.dayOfWeek
        println(number)
        return Random.nextInt(0, number).toString()
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
            in 1..5 -> 100
            6 -> 95
            7 -> 60
            8 -> 20
            else -> 1
        }
    }

    val testMultiplierRules = "(100 - 2 * (testId - 1))%, testId = 1.."
    val answerMultiplierRules = "attemptId = 1..5: 100%, attemptId = 6: 95%, attemptId = 7: 60%, attemptId = 8: 20%"

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