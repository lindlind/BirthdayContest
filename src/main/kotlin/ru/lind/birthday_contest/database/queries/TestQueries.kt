package ru.lind.birthday_contest.database.queries

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lind.birthday_contest.database.entities.DbTest
import ru.lind.birthday_contest.database.schema.TestsTable

object TestQueries {

    fun insert(test: DbTest) = transaction {
        TestsTable.insert {
            it[testId] = test.testId
            it[problemId] = test.problemId
            it[testIdInProblem] = test.testIdInProblem
            it[input] = test.input
            it[pricePercentage] = test.pricePercentage
            it[createdAt] = test.createdAt
        }
    }

    fun getLastCreated(problemId: Int?) = transaction {
        val testRow = problemId?.let {
            TestsTable
                .select { TestsTable.problemId eq problemId }
                .orderBy(TestsTable.createdAt to SortOrder.DESC)
                .limit(1)
                .firstOrNull()
        }
        return@transaction testRow?.let {
            DbTest(
                testRow[TestsTable.testId],
                testRow[TestsTable.problemId],
                testRow[TestsTable.testIdInProblem],
                testRow[TestsTable.input],
                testRow[TestsTable.pricePercentage],
                testRow[TestsTable.createdAt]
            )
        }
    }

    fun count(problemId: Int?) = transaction {
        problemId?.let {
            TestsTable.select { TestsTable.problemId eq problemId }.count()
        } ?: 0
    }

    fun countAll() = transaction {
        TestsTable.selectAll().count()
    }

}
