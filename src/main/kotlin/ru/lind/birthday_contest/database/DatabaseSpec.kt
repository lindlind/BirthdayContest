package ru.lind.birthday_contest.database

interface DatabaseSpec {
    val url: String
    val user: String
    val password: String
}

object ProdDatabaseSpec: DatabaseSpec {
    override val url = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d5dfeqk540sc4u?sslmode=require"
    override val user = "lbttjsjrknbzor"
    override val password = "6e15ab4c31293105ba1fe04ef78a09560ad56d7a306f8aa2706a4388c0e39716"
}

object DevDatabaseSpec: DatabaseSpec {
    override val url = "jdbc:postgresql://localhost:5432/birthday_contest"
    override val user = "postgres"
    override val password = "12345678"
}
