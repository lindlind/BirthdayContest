package ru.lind.birthday_contest.database.entities

import kotlin.math.ceil
import kotlin.math.roundToInt

data class DbPriceInfo(
    val problemPrice: Int,
    val testPricePercentage: Int,
    val attemptPricePercentage: Int
) {
    fun calculatePrice(): Int {
        var price = problemPrice.toDouble()
        price *= attemptPricePercentage.toDouble() / 100
        price *= testPricePercentage.toDouble() / 100
        return ceil(price).roundToInt()
    }
}
