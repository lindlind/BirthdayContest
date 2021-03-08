package ru.lind.birthday_contest.database.schema

import org.jetbrains.exposed.sql.Table

object ProblemsTable : Table("problems") {
    val problemId = integer("problem_id")
    val name = text("problem_name")
    val statement = text("statement")
    val price = integer("problem_price")
}
