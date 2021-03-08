package ru.lind.birthday_contest.problems

import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbAttempt
import ru.lind.birthday_contest.database.entities.DbTest
import ru.lind.birthday_contest.database.queries.AttemptQueries
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.database.queries.TestQueries
import ru.lind.birthday_contest.entities.*
import ru.lind.birthday_contest.entities.Problem
import java.lang.IllegalArgumentException
import kotlin.math.ceil
import kotlin.math.roundToInt

abstract class Problem: Problem {

    fun createNewTest(): Test {
        val input = generateTestInput()
        val test = saveTest(input)
        return test
    }

    fun checkAnswer(answer: String): AnswerAttempt {
        val test = getActualTest()
        val answerAttempt = saveAttempt(test.id, answer)

        val verdict = checkAnswer(test.input, answerAttempt.answer)
        answerAttempt.verdict = verdict

        updateVerdict(answerAttempt.id, verdict)
        return answerAttempt
    }

    fun calculateReward(answerId: Int): Int {
        val dbPriceInfo = ProblemQueries.getPriceInfo(answerId)
        return dbPriceInfo?.let {
            var price = it.problemPrice.toDouble()
            price *= it.attemptPricePercentage.toDouble() / 100
            price *= it.testPricePercentage.toDouble() / 100
            ceil(price).roundToInt()
        } ?: 0
    }

    fun getActualTest(): Test {
        val dbTest = TestQueries.getLastCreated(problemId)
        return dbTest?.let { Test(it) } ?: throw IllegalArgumentException("Test wasn't generated")
    }

    private fun saveTest(input: String): Test {
        val testId = TestQueries.countAll().toInt()
        val testIdInTest = TestQueries.count(problemId).toInt() + 1
        val dbTest = DbTest(
            testId,
            problemId,
            testIdInTest,
            input,
            calculateTestPricePercentage(testIdInTest),
            DateTime.now()
        )
        TestQueries.insert(dbTest)
        return Test(dbTest)
    }

    private fun saveAttempt(testId: Int, answer: String): AnswerAttempt {
        val attemptId = AttemptQueries.countAll().toInt()
        val attemptIdInTest = AttemptQueries.count(testId).toInt() + 1
        val dbAttempt = DbAttempt(
            attemptId,
            testId,
            attemptIdInTest,
            answer,
            calculateAnswerAttemptPricePercentage(attemptIdInTest),
            DateTime.now(),
            AnswerAttemptVerdict.PROCESSED
        )
        AttemptQueries.insert(dbAttempt)
        return AnswerAttempt(dbAttempt)
    }

    private fun updateVerdict(answerId: Int, verdict: AnswerAttemptVerdict) {
        AttemptQueries.updateVerdict(answerId, verdict)
    }

    protected abstract fun generateTestInput(): String

    protected abstract fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict

    protected abstract fun calculateTestPricePercentage(testIdInProblem: Int): Int

    protected abstract fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int

}
