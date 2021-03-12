package ru.lind.birthday_contest.problems

import org.joda.time.DateTime
import ru.lind.birthday_contest.api.Utils.toPercentageString
import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.api.models.ProblemStatsResponse
import ru.lind.birthday_contest.database.entities.DbAttempt
import ru.lind.birthday_contest.database.entities.DbTest
import ru.lind.birthday_contest.database.entities.calculatePrice
import ru.lind.birthday_contest.database.queries.AttemptQueries
import ru.lind.birthday_contest.database.queries.ProblemQueries
import ru.lind.birthday_contest.database.queries.TestQueries
import ru.lind.birthday_contest.models.*
import ru.lind.birthday_contest.models.Problem
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

abstract class Problem: Problem {

    fun createNewTest(): Test {
        val input = generateTestInput()
        val test = saveTest(input)
        return test
    }

    fun checkAnswer(answer: String): AnswerAttempt {
        val test = getActualTest() ?: throw IllegalArgumentException("Test wasn't generated")
        val answerAttempt = saveAttempt(test.id, answer)

        val (verdict, comment) = checkAnswer(test.input, answerAttempt.answer)
        answerAttempt.verdict = verdict
        answerAttempt.comment = comment

        updateVerdict(answerAttempt.id, verdict, comment)
        return answerAttempt
    }

    fun calculateReward(answerId: Int): Int {
        val dbPriceInfo = ProblemQueries.getPriceInfoByCurrentAttempt(answerId)
        return dbPriceInfo.calculatePrice()
    }

    fun getBestReward(): Int {
        val dbPriceInfo = ProblemQueries.getBestReward(problemId)
        return dbPriceInfo.calculatePrice()
    }

    fun getActualTest(): Test? {
        val dbTest = TestQueries.getLastCreated(problemId)
        return dbTest?.let { Test(it) }
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

    private fun updateVerdict(answerId: Int, verdict: AnswerAttemptVerdict, comment: String?) {
        AttemptQueries.updateVerdict(answerId, verdict, comment)
    }

    protected abstract fun generateTestInput(): String

    protected abstract fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?>

    protected abstract fun calculateTestPricePercentage(testIdInProblem: Int): Int

    protected abstract fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int

    companion object {

        fun getProblemStats(problemId: Int): ProblemStatsResponse {
            val dbProblem = ProblemQueries.get(problemId) ?: throw RuntimeException("Problem not found")
            val currentTest = TestQueries.getLastCreated(problemId)
            val testPricePercentage = currentTest?.pricePercentage ?: 100
            val lastAttempt = AttemptQueries.getLastCreated(currentTest?.testId)
            val attemptPricePercentage = lastAttempt?.pricePercentage ?: 100
            val bestReward = ProblemQueries.getBestReward(problemId)
            return ProblemStatsResponse(
                dbProblem.name,
                bestReward.calculatePrice().toRubString(),
                dbProblem.price.toRubString(),
                currentTest?.testIdInProblem ?: 1,
                testPricePercentage.toPercentageString(),
                lastAttempt?.attemptIdInTest ?: 0,
                attemptPricePercentage.toPercentageString()
            )
        }

    }

}
