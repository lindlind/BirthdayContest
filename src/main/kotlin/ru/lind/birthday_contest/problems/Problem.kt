package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.entities.*
import ru.lind.birthday_contest.entities.Problem

abstract class Problem: Problem {

    fun getNewTest(answer: String): Test {
        val input = generateTestInput()
        val test = saveTest(input)
        return test
    }

    fun handleAnswer(answer: String): AnswerAttempt {
        val test = getActualTest()
        var answerAttempt = saveAttempt(test.id, answer)
        val verdict = checkAnswer(test.input, answerAttempt.answer)
        answerAttempt = updateVerdict(answerAttempt.id, verdict)
        return answerAttempt
    }

    abstract fun calculateReward(answerId: Int): Long

    protected abstract fun getActualTest(): Test

    protected abstract fun saveTest(input: String): Test

    protected abstract fun saveAttempt(testId: Int, answer: String): AnswerAttempt

    protected abstract fun updateVerdict(answerId: Int, verdict: AnswerAttemptVerdict): AnswerAttempt

    protected abstract fun generateTestInput(): String

    protected abstract fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict

}
