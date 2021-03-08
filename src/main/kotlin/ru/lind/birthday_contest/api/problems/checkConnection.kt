package ru.lind.birthday_contest.api.problems

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.entities.AnswerAttemptVerdict
import ru.lind.birthday_contest.problems.CheckConnectionProblem

fun Route.checkConnectionProblem(endpoint: String) = route(endpoint) {

    val problem = CheckConnectionProblem.init()

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
    }

    get {
        val test = problem.getActualTest()
        call.respond(test)
    }

    post("/regen") {
        val test = problem.createNewTest()
        call.respond(test)
    }

    post("/answer") {
        val answer = call.receiveText()
        val checkedAnswer = problem.checkAnswer(answer)
        val reward = if (checkedAnswer.verdict == AnswerAttemptVerdict.ACCEPTED) {
            problem.calculateReward(checkedAnswer.id)
        } else 0
        call.respond(checkedAnswer to reward)
    }

}
