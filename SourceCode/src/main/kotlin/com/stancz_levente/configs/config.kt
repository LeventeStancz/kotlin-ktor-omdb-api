package com.stancz_levente.configs

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

// Configuration module for accessing application properties
object ConfigModule {
    private val config: ApplicationConfig by lazy {
        HoconApplicationConfig(ConfigFactory.load())
    }
    // Get property value based on the provided path
    fun getProperty(path: String): String {
        return config.property(path).getString()
    }
}