package ru.lind.birthday_contest.problems

import ru.lind.birthday_contest.entities.AnswerAttempt
import ru.lind.birthday_contest.entities.AnswerAttemptVerdict
import ru.lind.birthday_contest.entities.Test

class GraphNimProblem internal constructor(
    override val id: Int,
    override val name: String,
    override val statement: String,
    override val price: Int
) : Problem() {

    override fun calculateReward(answerId: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getActualTest(): Test {
        TODO("Not yet implemented")
    }

    override fun saveTest(input: String): Test {
        TODO("Not yet implemented")
    }

    override fun saveAttempt(testId: Int, answer: String): AnswerAttempt {
        TODO("Not yet implemented")
    }

    override fun updateVerdict(answerId: Int, verdict: AnswerAttemptVerdict): AnswerAttempt {
        TODO("Not yet implemented")
    }

    override fun generateTestInput(): String {
        TODO("Not yet implemented")
    }

    override fun checkAnswer(input: String, answer: String): AnswerAttemptVerdict {
        TODO("Not yet implemented")
    }

}