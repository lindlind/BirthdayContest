package ru.lind.birthday_contest.database.entities

data class DbPriceInfo(
    val problemPrice: Int,
    val testPricePercentage: Int,
    val attemptPricePercentage: Int
)
