package ru.lind.birthday_contest.models

interface Problem {
    val problemId: Int
    val name: String
    val statement: String
    val price: Int
}
