package ru.lind.birthday_contest.api

import com.google.gson.annotations.SerializedName
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.problems.Problem
import java.lang.IllegalArgumentException

fun Route.stats(endpoint: String) = route(endpoint) {

    val problemsStats = (0..5).map { Problem.getProblemStats(it) }

    get {
        call.respond(problemsStats)
    }

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
    }

}