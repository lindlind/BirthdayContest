package ru.lind.birthday_contest.entities

import com.google.gson.annotations.SerializedName
import ru.lind.birthday_contest.database.schema.DbEnum

enum class AnswerAttemptVerdict {

    @SerializedName("PROCESSED")
    PROCESSED,

    @SerializedName("ACCEPTED")
    ACCEPTED,

    @SerializedName("REJECTED")
    REJECTED,
    ;

    companion object: DbEnum<AnswerAttemptVerdict> {

        override fun get(value: String) = values().first { value == it.name }

    }

}