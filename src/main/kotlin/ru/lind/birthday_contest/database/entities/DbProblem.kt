package ru.lind.birthday_contest.database.entities

data class DbProblem(
    val problemId: Int,
    val name: String,
    val statement: String,
    val price: Int
)
