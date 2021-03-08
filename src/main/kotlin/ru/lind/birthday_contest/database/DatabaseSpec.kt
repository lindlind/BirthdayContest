package ru.lind.birthday_contest.database

interface DatabaseSpec {
    val url: String
    val user: String
    val password: String
}

object ProdDatabaseSpec: DatabaseSpec {
    override val url = ""
    override val user = ""
    override val password = ""
}

object DevDatabaseSpec: DatabaseSpec {
    override val url = ""
    override val user = ""
    override val password = ""
}
