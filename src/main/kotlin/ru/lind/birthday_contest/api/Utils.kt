package ru.lind.birthday_contest.api

import io.ktor.request.*
import org.joda.time.DateTime
import ru.lind.birthday_contest.TooManyRequestsException

internal object Utils {

    var lastApiQueryTime = DateTime.now()

    fun ApplicationRequest.isAdmin(): Boolean {
        return this.header("admin") == "lindvv"
    }

    fun ApplicationRequest.assertLowFrequency() {
        val now = DateTime.now()
        if (lastApiQueryTime.plusSeconds(1).isAfter(now)) {
            throw TooManyRequestsException()
        }
        lastApiQueryTime = now
    }

    fun Int.toRubString() = "$this RUB"

    fun Int.toPercentageString() = "$this%"

}
