package ru.lind.birthday_contest.api.problems.check_connection

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.api.models.AnswerResponse
import ru.lind.birthday_contest.api.models.EndpointInfo
import ru.lind.birthday_contest.api.models.ProblemResponse
import ru.lind.birthday_contest.api.models.TestResponse
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.CheckConnectionProblem

fun Route.checkConnectionProblem(endpoint: String) = route(endpoint) {

    val problem = CheckConnectionProblem.init()
    val endpoints = listOf(
        EndpointInfo("GET", "/info", "Получить информацию о задаче."),
        EndpointInfo("GET", "/test", "Получить текущий тест."),
        EndpointInfo("POST", "/regen", "Сгенерировать и получить новый тест."),
        EndpointInfo("POST", "/answer", "Отправить ответ на проверку.", "Text")
    )

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
    }

    get("/info") {
        val response = ProblemResponse(problem, endpoints)
        call.respond(response)
    }

    get("/test") {
        val test = problem.getActualTest() ?: problem.createNewTest()
        val response = TestResponse(test)
        call.respond(response)
    }

    post("/regen") {
        val test = problem.createNewTest()
        val response = TestResponse(test)
        call.respond(response)
    }

    post("/answer") {
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
