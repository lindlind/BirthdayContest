package ru.lind.birthday_contest.api

import com.google.gson.annotations.SerializedName
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import ru.lind.birthday_contest.api.models.EndpointInfo
import java.lang.IllegalArgumentException

fun Route.help(endpoint: String) = route(endpoint) {

    val endpoints = listOf(
        EndpointInfo("GET", "/help", "Помощь"),
        EndpointInfo("GET", "/{letter}/info", "Получить информацию о задаче."),
        EndpointInfo("GET", "/stats", "Получить текущие результаты.")
    )

    get {
        call.respond(endpoints)
    }

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
        call.respond("private rules")
    }

}
