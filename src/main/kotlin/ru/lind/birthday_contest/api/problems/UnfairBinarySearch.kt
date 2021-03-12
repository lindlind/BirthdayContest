package ru.lind.birthday_contest.api.problems.unfair_binary_search

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.api.Utils.assertLowFrequency
import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.api.models.*
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.CheckConnectionProblem
import ru.lind.birthday_contest.problems.UnfairBinarySearchProblem

fun Route.unfairBinarySearchProblem(endpoint: String) = route(endpoint) {

    val problem = UnfairBinarySearchProblem.init()
    val endpoints = listOf(
        EndpointInfo("GET", "/info", "Получить информацию о задаче."),
        EndpointInfo("GET", "/rewards", "Получить правила расчета выигрыша."),
        EndpointInfo("GET", "/test", "Получить информацию о текущем тесте."),
        EndpointInfo("POST", "/regen", "Сгенерировать новый тест."),
        EndpointInfo("POST", "/answer", "Отправить ответ на проверку.", "Text")
    )

    get("/info") {
        call.request.assertLowFrequency()
        val response = ProblemResponse(problem, endpoints)
        call.respond(response)
    }

    get("/rewards") {
        call.request.assertLowFrequency()
        val response = ProblemRewardRules(
            problem.name,
            problem.price.toRubString(),
            problem.testMultiplierRules,
            problem.answerMultiplierRules,
            problem.getBestReward().toRubString()
        )
        call.respond(response)
    }

    get("/test") {
        call.request.assertLowFrequency()
        val test = problem.getActualTest() ?: problem.createNewTest()
        val response = HiddenTestResponse(test)
        call.respond(response)
    }

    post("/regen") {
        call.request.assertLowFrequency()
        val test = problem.createNewTest()
        val response = HiddenTestResponse(test)
        call.respond(response)
    }

    post("/answer") {
        call.request.assertLowFrequency()
        val answer = call.receiveText()
        if (answer.isBlank()) {
            throw IllegalArgumentException("Answer should be a non-empty string")
        }
        val checkedAnswer = problem.checkAnswer(answer)
        val reward = if (checkedAnswer.verdict == AnswerAttemptVerdict.ACCEPTED) {
            problem.calculateReward(checkedAnswer.id)
        } else 0
        val response = AnswerResponse(checkedAnswer, reward)
        call.respond(response)
    }

}
