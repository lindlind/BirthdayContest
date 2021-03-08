import io.ktor.routing.*
import ru.lind.birthday_contest.api.help
import ru.lind.birthday_contest.api.problems.checkConnectionProblem

fun Route.endpoints() {

    help("/help")
    route("/problems") {
        checkConnectionProblem("/A")
    }

}
