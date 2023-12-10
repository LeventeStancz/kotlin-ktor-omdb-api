package com.stancz_levente.routes

import com.stancz_levente.converters.toDocument
import com.stancz_levente.converters.toUser
import com.stancz_levente.documents.UserDoc
import com.stancz_levente.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRoutes() {
    val userService = UserService()

    routing {
        route("/users") {
            // Create a new user
            post("/create") {
                val request = call.receive<UserDoc>()
                val user = request.toUser()

                userService.create(user)
                    ?.let { userId ->
                        call.response.headers.append("userId", userId.toString())
                        call.respond(HttpStatusCode.OK, "Successfully created user")
                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid input data")
            }
            // Get user by ID
            get("/{id}") {
                val id = call.parameters["id"].toString()
                userService.findById(id)
                    ?.let { foundUser -> call.respond(HttpStatusCode.OK, foundUser.toDocument()) }
                    ?: call.respond(HttpStatusCode.NotFound, "User not found")
            }
            // Update user data by ID
            put("/update/{id}") {
                val id = call.parameters["id"].toString()
                val userRequest = call.receive<UserDoc>()
                val user = userRequest.toUser()
                val updatedSuccessfully = userService.updateById(id, user)
                if (updatedSuccessfully) {
                    call.respond(HttpStatusCode.OK, "Successfully updated user data")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid input data")
                }
            }
            // Delete user by ID
            delete("/delete/{id}") {
                val id = call.parameters["id"].toString()
                val deletedSuccessfully = userService.deleteById(id)
                if (deletedSuccessfully) {
                    call.respond(HttpStatusCode.OK, "Successfully deleted user")
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            }
        }
    }
}