package ru.lind.birthday_contest.database.queries

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbGameOfLifeTest
import ru.lind.birthday_contest.database.schema.GameOfLifeTestsTable
import ru.lind.birthday_contest.database.schema.LabyrinthWithTeleportsTestsTable

object GameOfLifeTestQueries {

    fun getNotUsed() = transaction {
        val testRow = GameOfLifeTestsTable
            .select { GameOfLifeTestsTable.usedAt.isNull() }
            .firstOrNull()
        return@transaction testRow?.let {
            DbGameOfLifeTest(
                testRow[GameOfLifeTestsTable.testId],
                testRow[GameOfLifeTestsTable.input],
                testRow[GameOfLifeTestsTable.answer],
                testRow[GameOfLifeTestsTable.usedAt]
            )
        }
    }

    fun setUsed(testId: Int) = transaction {
        GameOfLifeTestsTable
            .update({ GameOfLifeTestsTable.testId eq testId })
            { it[usedAt] = DateTime.now() }
    }

    fun getLastUsed() = transaction {
        val testRow = GameOfLifeTestsTable
            .select { GameOfLifeTestsTable.usedAt.isNotNull() }
            .orderBy(GameOfLifeTestsTable.usedAt to SortOrder.DESC)
            .firstOrNull()
        return@transaction testRow?.let {
            DbGameOfLifeTest(
                testRow[GameOfLifeTestsTable.testId],
                testRow[GameOfLifeTestsTable.input],
                testRow[GameOfLifeTestsTable.answer],
                testRow[GameOfLifeTestsTable.usedAt]
            )
        }
    }

}
