package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.database.entities.DbProblem
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.entities.AnswerAttemptVerdict
import kotlin.math.max
import kotlin.random.Random

class CheckConnectionProblem internal constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        val stringLength = Random.nextInt(500, 5000)
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..stringLength)
            .map { charPool[Random.nextInt(0, charPool.size)] }
            .joinToString("")
    }

    override fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict {
        return if (input.reversed() == answer) {
            AnswerAttemptVerdict.ACCEPTED
        } else {
            AnswerAttemptVerdict.REJECTED
        }
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        return max(1, 100 - 2 * (testIdInProblem - 1))
    }

    override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        return max(1, 100 - 1 * (answerIdInTest - 1))
    }

    companion object {

        private const val PROBLEM_ID: Int = 0

         fun init() : CheckConnectionProblem {
             val dbProblem = ProblemQueries.get(PROBLEM_ID)
             return dbProblem?.let {
                 CheckConnectionProblem(
                     dbProblem.problemId,
                     dbProblem.name,
                     dbProblem.statement,
                     dbProblem.price
                 )
             } ?: throw IllegalStateException("Can't find problem $PROBLEM_ID in database")
         }

    }

}