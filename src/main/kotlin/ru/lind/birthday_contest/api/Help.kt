package ru.lind.birthday_contest.api

import com.google.gson.annotations.SerializedName
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.lind.birthday_contest.api.Utils.isAdmin
import java.lang.IllegalArgumentException

fun Route.help(endpoint: String) = route(endpoint) {

    get {
        val rules = "kek"
        call.respond(HelpResponse(rules))
    }

    post {
        if (!call.request.isAdmin()) {
            throw IllegalArgumentException()
        }
        call.respond(HelpResponse("private rules"))
    }

}

private data class HelpResponse(
    @SerializedName("rules")
    val rules: String
)