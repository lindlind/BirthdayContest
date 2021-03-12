package ru.lind.birthday_contest.database.queries

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lind.birthday_contest.database.entities.DbAttempt
import ru.lind.birthday_contest.database.entities.DbTest
import ru.lind.birthday_contest.database.schema.AttemptsTable
import ru.lind.birthday_contest.database.schema.TestsTable
import ru.lind.birthday_contest.models.AnswerAttemptVerdict

object AttemptQueries {

    fun insert(attempt: DbAttempt) = transaction {
        AttemptsTable.insert {
            it[attemptId] = attempt.attemptId
            it[testId] = attempt.testId
            it[attemptIdInTest] = attempt.attemptIdInTest
            it[answer] = attempt.answer
            it[pricePercentage] = attempt.pricePercentage
            it[createdAt] = attempt.createdAt
            it[verdict] = attempt.verdict
            it[comment] = attempt.comment
        }
    }

    fun updateVerdict(attemptId: Int?, verdict: AnswerAttemptVerdict, comment: String?) = transaction {
        attemptId?.let {
            AttemptsTable.update( {
                AttemptsTable.attemptId eq attemptId
            } ) {
                it[AttemptsTable.verdict] = verdict
                it[AttemptsTable.comment] = comment
            }
        }
    }

    fun getLastCreated(testId: Int?) = transaction {
        val attemptRow = testId?.let {
            AttemptsTable
                .select { AttemptsTable.testId eq testId }
                .orderBy(AttemptsTable.createdAt to SortOrder.DESC)
                .limit(1)
                .firstOrNull()
        }
        return@transaction attemptRow?.let {
            DbAttempt(
                attemptRow[AttemptsTable.attemptId],
                attemptRow[AttemptsTable.testId],
                attemptRow[AttemptsTable.attemptIdInTest],
                attemptRow[AttemptsTable.answer],
                attemptRow[AttemptsTable.pricePercentage],
                attemptRow[AttemptsTable.createdAt],
                attemptRow[AttemptsTable.verdict],
                attemptRow[AttemptsTable.comment]
            )
        }
    }

    fun count(testId: Int?) = transaction {
        testId?.let {
            AttemptsTable.select { AttemptsTable.testId eq testId }.count()
        } ?: 0
    }

    fun countAll() = transaction {
        AttemptsTable.selectAll().count()
    }

}
