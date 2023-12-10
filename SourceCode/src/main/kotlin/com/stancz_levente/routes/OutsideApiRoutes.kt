package com.stancz_levente.routes
import com.stancz_levente.configs.ConfigModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*

fun Application.configureOutsideApiRoutes() {
    routing {
        route("/search") {
            // Search in the external API with movie id
            get("/byId/{movieId}") {
                val apiKey = ConfigModule.getProperty("OMDb_api.key")
                val movieId = call.parameters["movieId"].toString()
                val url = "http://www.omdbapi.com/?i=$movieId&apikey=$apiKey"
                sendGetRequest(url, call)
            }
            // Search in the external API with the given parameters
            get("/{movieTitle}/{year?}/{type?}") {
                val apiKey = ConfigModule.getProperty("OMDb_api.key")
                val movieTitle = call.parameters["movieTitle"]
                val year = call.parameters["year"]
                val type = call.parameters["type"]
                if(movieTitle != null){
                    val yearString = if (year != null && year.toIntOrNull() != null) "&y=$year" else ""
                    val typeString = if (type != null && (type == "movie" || type == "series" || type == "episode")) "&type=$type" else ""
                    val url = "http://www.omdbapi.com/?s=$movieTitle$yearString$typeString&apikey=$apiKey"
                    sendGetRequest(url, call)
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Missing required parameters")
                }
            }
        }
    }
}

suspend fun sendGetRequest(url: String, call: ApplicationCall) {
    val client = HttpClient()
    try {
        val response: HttpResponse = client.request(url) {
            method = HttpMethod.Get
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                call.respond(HttpStatusCode.OK, response.bodyAsText())
            }
            HttpStatusCode.NotFound -> {
                call.respond(HttpStatusCode.NotFound, "Movie not found")
            }
            HttpStatusCode.Unauthorized -> {
                call.respond(HttpStatusCode.Unauthorized, "Invalid API key")
            }
            else -> {
                call.respond(HttpStatusCode.InternalServerError, "Something went wrong")
            }
        }
    } catch (e: Exception) {
        call.respond(HttpStatusCode.InternalServerError, "Error fetching data from OMDb API")
    } finally {
        client.close()
    }
}