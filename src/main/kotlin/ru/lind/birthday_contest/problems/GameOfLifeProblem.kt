package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.models.AnswerAttemptVerdict

class GameOfLifeProblem internal constructor(
    override val problemId: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun generateTestInput(): String {
        TODO("Not yet implemented")
    }

    override fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict {
        TODO("Not yet implemented")
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        TODO("Not yet implemented")
    }

    override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        TODO("Not yet implemented")
    }

}