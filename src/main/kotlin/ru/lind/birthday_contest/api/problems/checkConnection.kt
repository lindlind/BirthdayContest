package ru.lind.birthday_contest.api.problems

import io.ktor.application.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin

fun Route.checkConnectionProblem(endpoint: String) = route(endpoint) {

    get {
    }

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
    }

}