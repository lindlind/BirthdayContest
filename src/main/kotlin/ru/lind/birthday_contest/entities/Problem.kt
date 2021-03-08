package ru.lind.birthday_contest.entities

interface Problem {
    val problemId: Int
    val name: String
    val statement: String
    val price: Int
}