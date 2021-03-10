package ru.lind.birthday_contest.problems.graph_nim_problem

import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.Problem
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

abstract class GraphNimProblem : Problem() {

    private val MIN_BEAM_LENGTH = 1e3.toInt()
    private val MAX_BEAM_LENGTH = 1e7.toInt()

    final override fun generateTestInput(): String {
        val number1 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
        val number2 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
        val number3 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
        return "$number1 $number2 $number3"
    }

    abstract fun getNimSum(vararg nums: Int): Int

    final override fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?> {
        val numbers = input.splitToSequence(' ').map { it.toInt() % 12 }.toList()
        var expectedAnswer = 50
        for (i1 in 0..12) for (i2 in 0..12) for (i3 in 0..12) {
            var i = 0
            val n1 = numbers[i++] + i1
            val n2 = numbers[i++] + i2
            val n3 = numbers[i++] + i3

            var nimSum = getNimSum(n1 - 1, n2 - 1, n3 - 1)
            nimSum = min(nimSum, getNimSum(n1, n2 - 1, n3 - 1))
            nimSum = min(nimSum, getNimSum(n1 - 1, n2, n3 - 1))
            nimSum = min(nimSum, getNimSum(n1 - 1, n2 - 1, n3))
            nimSum = min(nimSum, getNimSum(n1 - 1, n2 + n3))
            nimSum = min(nimSum, getNimSum(n2 - 1, n3 + n1))
            nimSum = min(nimSum, getNimSum(n3 - 1, n1 + n2))
            if (nimSum == 0) {
                expectedAnswer = min(expectedAnswer, i1 + i2 + i3)
            }
        }
        if (expectedAnswer < answer.toIntOrNull() ?: 100) {
            return AnswerAttemptVerdict.ACCEPTED to null
        } else {
            return AnswerAttemptVerdict.REJECTED to null
        }
    }

    override fun calculateTestPricePercentage(testIdInProblem: Int): Int {
        return max(1, 100 - 2 * (testIdInProblem - 1))
    }

    final override fun calculateAnswerAttemptPricePercentage(answerIdInTest: Int): Int {
        return when(answerIdInTest) {
            in 1..3 -> 100
            4 -> 60
            5 -> 20
            else -> 1
        }
    }

}