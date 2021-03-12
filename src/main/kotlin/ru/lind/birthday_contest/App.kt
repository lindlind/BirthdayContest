package ru.lind.birthday_contest

import endpoints
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import ru.lind.birthday_contest.database.Database
import java.lang.IllegalArgumentException

const val PROD_URL = ""
const val DEV_URL_DOMAIN = "localhost"
const val DEV_URL = "$DEV_URL_DOMAIN:3000"

fun Application.main() {
    Database.connect()

    install(DefaultHeaders)
    install(CallLogging)
    install(CORS) {
        method(HttpMethod.Post)
        method(HttpMethod.Get)
        anyHost()
        //host(PROD_URL, listOf("https"))
        host(DEV_URL)
        allowCredentials = true
        allowNonSimpleContentTypes = true
    }
    install(WebSockets)
    install(StatusPages) {
        exception<IllegalArgumentException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad Request")
        }
        exception<TooManyRequestsException> { cause ->
            call.respond(HttpStatusCode.TooManyRequests)
        }
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
        }
    }
    routing {
        endpoints()
    }

}