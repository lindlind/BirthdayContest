package ru.lind.birthday_contest.problems

import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbAttempt
import ru.lind.birthday_contest.database.entities.DbTest
import ru.lind.birthday_contest.database.queries.AttemptQueries
import ru.lind.birthday_contest.database.queries.TestQueries
import ru.lind.birthday_contest.entities.*
import ru.lind.birthday_contest.entities.Problem
import java.lang.IllegalArgumentException

abstract class Problem: Problem {

    fun getNewTest(): Test {
        val input = generateTestInput()
        val test = saveTest(input)
        return test
    }

    fun handleAnswer(answer: String): AnswerAttempt {
        val test = getActualTest() ?: throw IllegalArgumentException("Test wasn't generated. Nothing to check with")
        val answerAttempt = saveAttempt(test.id, answer)

        val verdict = checkAnswer(test.input, answerAttempt.answer)
        answerAttempt.verdict = verdict

        updateVerdict(answerAttempt.id, verdict)
        return answerAttempt
    }

    abstract fun calculateReward(answerId: Int): Long

    private fun getActualTest(): Test? {
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

    private fun updateVerdict(answerId: Int, verdict: AnswerAttemptVerdict) {
        AttemptQueries.updateVerdict(answerId, verdict)
    }

    protected abstract fun generateTestInput(): String

    protected abstract fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict

    protected abstract fun calculateTestPricePercentage(testIdInProblem: Int): Int

    protected abstract fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int

}
