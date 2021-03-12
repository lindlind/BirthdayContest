package ru.lind.birthday_contest.api.problems.unfair_binary_search

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.api.Utils.assertLowFrequency
import ru.lind.birthday_contest.api.Utils.toRubString
import ru.lind.birthday_contest.api.models.*
import ru.lind.birthday_contest.models.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.CheckConnectionProblem
import ru.lind.birthday_contest.problems.UnfairBinarySearchProblem
import ru.lind.birthday_contest.problems.graph_nim_problem.GraphNimProblem
import ru.lind.birthday_contest.problems.graph_nim_problem.GraphNimProblemEasy
import ru.lind.birthday_contest.problems.graph_nim_problem.GraphNimProblemHard

fun Route.graphNimProblem(endpoint: String) = route(endpoint) {

    val problemEasy = GraphNimProblemEasy.init()
    val endpointsEasy = listOf(
        EndpointInfo("GET", "/easy/info", "Получить информацию о легкой версии задачи."),
        EndpointInfo("GET", "/easy/rewards", "Получить правила расчета выигрыша за легкую версию."),
        EndpointInfo("GET", "/easy/test", "Получить текущий тест на легкую версию."),
        EndpointInfo("POST", "/easy/regen", "Сгенерировать и получить новый тест на легкую версию задачи."),
        EndpointInfo("POST", "/easy/answer", "Отправить ответ на легую версию задачи на проверку.", "Text")
    )

    val problemHard = GraphNimProblemHard.init()
    val endpointsHard = listOf(
        EndpointInfo("GET", "/hard/info", "Получить информацию о сложной версии задачи."),
        EndpointInfo("GET", "/hard/rewards", "Получить правила расчета выигрыша за сложную версию."),
        EndpointInfo("GET", "/hard/test", "Получить текущий тест на сложную версию."),
        EndpointInfo("POST", "/hard/regen", "Сгенерировать и получить новый тест на сложную версию задачи."),
        EndpointInfo("POST", "/hard/answer", "Отправить ответ на сложную версию задачи проверку.", "Text")
    )

    suspend fun PipelineContext<Unit, ApplicationCall>.rewards(problem: GraphNimProblem) {
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

    suspend fun PipelineContext<Unit, ApplicationCall>.test(problem: GraphNimProblem) {
        call.request.assertLowFrequency()
        val test = problem.getActualTest() ?: problem.createNewTest()
        val response = TestResponse(test)
        call.respond(response)
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.regen(problem: GraphNimProblem) {
        call.request.assertLowFrequency()
        val test = problem.createNewTest()
        val response = TestResponse(test)
        call.respond(response)
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.answer(problem: GraphNimProblem) {
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

    get("/info") {
        call.request.assertLowFrequency()
        val response = ProblemResponse(problemHard, listOf(endpointsEasy, endpointsHard).flatten())
        call.respond(response)
    }

    get("/easy/info") {
        call.request.assertLowFrequency()
        val response = ProblemResponse(problemEasy, endpointsEasy)
        call.respond(response)
    }

    get("/hard/info") {
        call.request.assertLowFrequency()
        val response = ProblemResponse(problemHard, endpointsHard)
        call.respond(response)
    }

    get("/easy/rewards") { rewards(problemEasy) }
    get("/hard/rewards") { rewards(problemHard) }

    get("/easy/test") { test(problemEasy) }
    get("/hard/test") { test(problemHard) }

    post("/easy/regen") { regen(problemEasy) }
    post("/hard/regen") { regen(problemHard) }

    post("/easy/answer") { answer(problemEasy) }
    post("/hard/answer") { answer(problemHard) }

}
