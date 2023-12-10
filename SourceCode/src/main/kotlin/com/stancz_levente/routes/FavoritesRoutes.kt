package com.stancz_levente.routes

import com.stancz_levente.converters.toDocument
import com.stancz_levente.services.FavoritesService
import com.stancz_levente.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Configure routes for user favorites
fun Application.configureFavoritesRoutes() {
    val favoritesService = FavoritesService()
    val userService = UserService()

    routing {
        route("/favorites") {
            // Get favorites by user ID
            get("/byUser/{id}") {
                val id = call.parameters["id"].toString()
                favoritesService.findFavoritesByUserId(id)
                    ?.let { foundUserFavorites -> call.respond(HttpStatusCode.OK, foundUserFavorites.toDocument()) }
                    ?: call.respond(HttpStatusCode.NotFound, "User does not have favorites or do not exist")
            }
            // Add a movie to user favorites
            put("/add/{userId}/{movieId}") {
                val userId = call.parameters["userId"].toString()
                val movieId = call.parameters["movieId"].toString()
                val user = userService.findById(userId)
                if (user != null) {
                    val updatedSuccessfully = favoritesService.appendFavoritesById(userId, movieId)
                    if (updatedSuccessfully) {
                        call.respond(HttpStatusCode.OK, "Successfully added movie")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Invalid input data")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }
            // Remove a movie from user favorites
            delete("/remove/{userId}/{movieId}") {
                val userId = call.parameters["userId"].toString()
                val movieId = call.parameters["movieId"].toString()
                val user = userService.findById(userId)
                if (user != null) {
                    val updatedSuccessfully = favoritesService.removeFromFavoritesById(userId, movieId)
                    if (updatedSuccessfully) {
                        call.respond(HttpStatusCode.OK, "Successfully removed movie")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Invalid input data")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                }
            }
        }
    }
}