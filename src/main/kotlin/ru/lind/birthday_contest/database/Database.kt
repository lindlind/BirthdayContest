package ru.lind.birthday_contest.database

import org.jetbrains.exposed.sql.Database

object Database {

    fun connect() = connect(ProdDatabaseSpec)

    fun devConnect() = connect(DevDatabaseSpec)

    private fun connect(spec: DatabaseSpec) {
        Database.connect(
            url = spec.url,
            user = spec.user,
            password = spec.password
        )
    }

}
