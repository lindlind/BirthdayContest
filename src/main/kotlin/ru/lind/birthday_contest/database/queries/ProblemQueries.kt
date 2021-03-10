package ru.lind.birthday_contest.database.queries

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lind.birthday_contest.database.entities.DbPriceInfo
import ru.lind.birthday_contest.database.entities.DbProblem
import ru.lind.birthday_contest.database.schema.AttemptsTable
import ru.lind.birthday_contest.database.schema.ProblemsTable
import ru.lind.birthday_contest.database.schema.TestsTable
import ru.lind.birthday_contest.models.AnswerAttemptVerdict

object ProblemQueries {

    private val ComplexTable = AttemptsTable.innerJoin(
        TestsTable, { AttemptsTable.testId }, { TestsTable.testId }
    ).innerJoin(
        ProblemsTable, { TestsTable.problemId }, { ProblemsTable.problemId }
    )

    fun getPriceInfo(attemptId: Int?) = transaction {
        val priceInfoRow = attemptId?.let {
            ComplexTable.select {
                (AttemptsTable.attemptId eq attemptId) and (AttemptsTable.verdict eq AnswerAttemptVerdict.ACCEPTED)
            }.firstOrNull()
        }
        return@transaction priceInfoRow?.let {
            DbPriceInfo(
                priceInfoRow[ProblemsTable.price],
                priceInfoRow[TestsTable.pricePercentage],
                priceInfoRow[AttemptsTable.pricePercentage]
            )
        }
    }

    fun get(problemId: Int?) = transaction {
        val problemRow = problemId?.let {
            ProblemsTable.select { ProblemsTable.problemId eq problemId }.firstOrNull()
        }
        return@transaction problemRow?.let {
            DbProblem(
                problemRow[ProblemsTable.problemId],
                problemRow[ProblemsTable.name],
                problemRow[ProblemsTable.statement],
                problemRow[ProblemsTable.price]
            )
        }
    }

}
