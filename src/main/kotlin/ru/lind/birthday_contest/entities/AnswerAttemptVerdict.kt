package ru.lind.birthday_contest.entities

import com.google.gson.annotations.SerializedName

enum class AnswerAttemptVerdict {

    @SerializedName("PROCESSED")
    PROCESSED,

    @SerializedName("ACCEPTED")
    ACCEPTED,

    @SerializedName("REJECTED")
    REJECTED,
    ;

}