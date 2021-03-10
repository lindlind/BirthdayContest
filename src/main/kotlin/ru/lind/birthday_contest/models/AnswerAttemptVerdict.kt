package ru.lind.birthday_contest.models

import com.google.gson.annotations.SerializedName
import ru.lind.birthday_contest.database.schema.DbEnum

enum class AnswerAttemptVerdict(val value: String) {

    @SerializedName("PROCESSED")
    PROCESSED("PROCESSED"),

    @SerializedName("ACCEPTED")
    ACCEPTED("ACCEPTED"),

    @SerializedName("REJECTED")
    REJECTED("REJECTED"),
    ;

    companion object: DbEnum<AnswerAttemptVerdict> {

        override fun get(value: String) = values().first { value == it.value }

    }

}
