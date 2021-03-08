package ru.lind.birthday_contest.api

import io.ktor.request.*

internal object Utils {

    fun ApplicationRequest.isAdmin(): Boolean {
        return this.header("admin") == "lindvv"
    }

}
