package backend.data.api.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.rootRoute() {
    route("/") {
        get {
            call.respondText(
                text = "Hello, Backend on Kotlin powered by Ktor on Tomcat!",
                status = HttpStatusCode.OK
            )
        }
    }
}