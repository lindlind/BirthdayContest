package ru.lind.birthday_contest.problems.graph_nim_problem

import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.Problem
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

abstract class GraphNimProblem : Problem() {

    private val MIN_BEAM_LENGTH = 1e3.toInt()
    private val MAX_BEAM_LENGTH = 1e7.toInt()

    abstract val minAns: Int

    final override fun generateTestInput(): String {
        lateinit var input: String
        while (true) {
            val number1 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
            val number2 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
            val number3 = Random.nextInt(MIN_BEAM_LENGTH, MAX_BEAM_LENGTH)
            input = "$number1 $number2 $number3"
            if (isGoodInput(input)) {
                break
            }
        }
        return input
    }

    private fun isGoodInput(input: String): Boolean {
        for (i1 in 0 until minAns) for (i2 in 0 until minAns - i1) for (i3 in 0 until minAns - i1 - i2) {
            if (checkAnswer(input, "$i1 $i2 $i3").first == AnswerAttemptVerdict.ACCEPTED) {
                return false
            }
        }
        return true
    }

    abstract fun getNimSum(vararg nums: Int): Int

    final override fun checkAnswer(input: String, answer: String): Pair<AnswerAttemptVerdict, String?> {
        val inputNumbers = input.splitToSequence(' ').map { it.toInt() % 12 }.toList()
        val answerNumbers = answer.splitToSequence(' ').mapNotNull { it.toIntOrNull() }.toList()
        if (answerNumbers.size != 3) {
            throw IllegalArgumentException("Error while parsing answer")
        }
        var expectedAnswer = 50
        for (i1 in 0..12) for (i2 in 0..12) for (i3 in 0..12) {
            val nimSum = getNimSumOfBestStep(
                inputNumbers[0] + i1,
                inputNumbers[1] + i2,
                inputNumbers[2] + i3
            )
            if (nimSum == 0) {
                expectedAnswer = min(expectedAnswer, i1 + i2 + i3)
            }
        }
        if (expectedAnswer < answerNumbers.fold(0) { acc, n -> acc + n }) {
            return AnswerAttemptVerdict.REJECTED to null
        }

        val nimSum = getNimSumOfBestStep(
            inputNumbers[0] + answerNumbers[0],
            inputNumbers[1] + answerNumbers[1],
            inputNumbers[2] + answerNumbers[2]
        )

        return if (nimSum == 0) {
            AnswerAttemptVerdict.ACCEPTED to null
        } else {
            AnswerAttemptVerdict.REJECTED to null
        }
    }

    private fun getNimSumOfBestStep(n1: Int, n2: Int, n3: Int): Int {
        var nimSum = getNimSum(n1 - 1, n2 - 1, n3 - 1)
        nimSum = min(nimSum, getNimSum(n1, n2 - 1, n3 - 1))
        nimSum = min(nimSum, getNimSum(n1 - 1, n2, n3 - 1))
        nimSum = min(nimSum, getNimSum(n1 - 1, n2 - 1, n3))
        nimSum = min(nimSum, getNimSum(n1 - 1, n2 + n3))
        nimSum = min(nimSum, getNimSum(n2 - 1, n3 + n1))
        nimSum = min(nimSum, getNimSum(n3 - 1, n1 + n2))
        return nimSum
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

    val testMultiplierRules = "(100 - 2 * (testId - 1))%, testId = 1.."
    val answerMultiplierRules = "attemptId = 1..3: 100%, attemptId = 4: 60%, attemptId = 5: 20%"

}