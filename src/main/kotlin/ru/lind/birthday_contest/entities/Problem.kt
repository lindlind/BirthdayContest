package ru.lind.birthday_contest.entities

interface Problem {
    val id: Int
    val name: String
    val statement: String
    val price: Int
}