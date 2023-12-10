package com.stancz_levente

import com.stancz_levente.routes.configureFavoritesRoutes
import com.stancz_levente.routes.configureOutsideApiRoutes
import com.stancz_levente.routes.configureUserRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Install Content Negotiation for JSON support
    install(ContentNegotiation) {
        json()
    }
    // Configure routes for user, favorites, and external API
    configureUserRoutes()
    configureFavoritesRoutes()
    configureOutsideApiRoutes()
}
