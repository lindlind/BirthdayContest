import io.ktor.routing.*
import ru.lind.birthday_contest.api.help
import ru.lind.birthday_contest.api.problems.check_connection.checkConnectionProblem
import ru.lind.birthday_contest.api.problems.check_connection.gameOfLifeProblem
import ru.lind.birthday_contest.api.problems.check_connection.labyrinthWithTeleportsProblem
import ru.lind.birthday_contest.api.problems.unfair_binary_search.graphNimProblem
import ru.lind.birthday_contest.api.problems.unfair_binary_search.unfairBinarySearchProblem

fun Route.endpoints() {

    help("/help")
    route("/problems") {
        checkConnectionProblem("/A")
        unfairBinarySearchProblem("/B")
        graphNimProblem("/C")
        gameOfLifeProblem("/D")
        labyrinthWithTeleportsProblem("/E")
    }
    help("/stats")

}
