package backend.data.api.routes.user

import backend.data.api.models.user.User
import backend.data.repositories.user.UserRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.instance
import org.kodein.di.ktor.di

@ExperimentalSerializationApi
fun Routing.userRoute() {
    val userRepository: UserRepository by di().instance()
    route("/user") {
        handleGetAllUsers(userRepository = userRepository)
        handlePostUser(userRepository = userRepository)
        handleDeleteUser(userRepository = userRepository)
        handleGetUser(userRepository = userRepository)
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetAllUsers(userRepository: UserRepository) {
    get {
        val allUsers = userRepository.getAllUsers()
        if (allUsers.isNotEmpty()) {
            call.respond(allUsers)
        } else {
            call.respondText(
                text = "No users found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handlePostUser(userRepository: UserRepository) {
    post {
        val user = call.receive<User>()
        userRepository.addUser(user)
        call.respondText(
            text = "User added",
            status = HttpStatusCode.Created
        )
    }
}

private fun Route.handleDeleteUser(userRepository: UserRepository) {
    delete("{userId}") {
        val userId = call.parameters["userId"]
            ?.toLongOrNull()
            ?: return@delete call.respondText(
                text = "Missing userId",
                status = HttpStatusCode.NotFound
            )
        try {
            userRepository.deleteUser(userId)
            call.respondText(
                text = "User deleted",
                status = HttpStatusCode.Accepted
            )
        } catch (e: EntityNotFoundException) {
            return@delete call.respondText(
                text = "User with userId = $userId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}

@ExperimentalSerializationApi
private fun Route.handleGetUser(userRepository: UserRepository) {
    get("{userId}") {
        val userId = call.parameters["userId"]
            ?.toLongOrNull()
            ?: return@get call.respondText(
                text = "Missing userId",
                status = HttpStatusCode.NotFound
            )
        try {
            val user = userRepository.getUser(userId)
            call.respond(user)
        } catch (e: EntityNotFoundException) {
            return@get call.respondText(
                text = "User with userId = $userId not found",
                status = HttpStatusCode.NotFound
            )
        }
    }
}
