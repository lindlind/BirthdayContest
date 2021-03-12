package ru.lind.birthday_contest.database.queries

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ru.lind.birthday_contest.database.entities.DbGameOfLifeTest
import ru.lind.birthday_contest.database.entities.DbLabyrinthWithTeleportsTest
import ru.lind.birthday_contest.database.schema.GameOfLifeTestsTable
import ru.lind.birthday_contest.database.schema.LabyrinthWithTeleportsTestsTable

object LabyrinthWithTeleportsQueries {

    fun getNotUsed() = transaction {
        val testRow = LabyrinthWithTeleportsTestsTable
            .select { LabyrinthWithTeleportsTestsTable.usedAt.isNull() }
            .firstOrNull()
        return@transaction testRow?.let {
            DbLabyrinthWithTeleportsTest(
                testRow[LabyrinthWithTeleportsTestsTable.testId],
                testRow[LabyrinthWithTeleportsTestsTable.input],
                testRow[LabyrinthWithTeleportsTestsTable.usedAt]
            )
        }
    }

    fun setUsed(testId: Int) = transaction {
        LabyrinthWithTeleportsTestsTable
            .update({ LabyrinthWithTeleportsTestsTable.testId eq testId })
            { it[usedAt] = DateTime.now() }
    }

    fun getLastUsed() = transaction {
        val testRow = GameOfLifeTestsTable
            .select { LabyrinthWithTeleportsTestsTable.usedAt.isNotNull() }
            .orderBy(LabyrinthWithTeleportsTestsTable.usedAt to SortOrder.DESC)
            .firstOrNull()
        return@transaction testRow?.let {
            DbLabyrinthWithTeleportsTest(
                testRow[LabyrinthWithTeleportsTestsTable.testId],
                testRow[LabyrinthWithTeleportsTestsTable.input],
                testRow[LabyrinthWithTeleportsTestsTable.usedAt]
            )
        }
    }

}
