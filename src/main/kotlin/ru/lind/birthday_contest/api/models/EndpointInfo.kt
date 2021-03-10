package ru.lind.birthday_contest.api.models

data class EndpointInfo(
    val type: String,
    val endpoint: String,
    val description: String,
    val body: String = "None"
)
