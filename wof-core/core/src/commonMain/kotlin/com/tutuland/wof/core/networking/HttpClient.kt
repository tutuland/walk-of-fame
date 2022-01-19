package com.tutuland.wof.core.networking

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.BuildKonfig.API_KEY
import com.tutuland.wof.core.BuildKonfig.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json

fun HttpRequestBuilder.makeUrlFor(path: String) {
    val cleanPath = path.dropWhile { it == '/' }.replace(" ", "%20")
    val jointWith = if (cleanPath.contains('?')) '&' else '?'
    val apiKey = "api_key=$API_KEY"
    val finalUrl = buildString {
        append(BASE_URL)
        append(cleanPath)
        append(jointWith)
        append(apiKey)
    }
    url(finalUrl)
}

fun makeHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                Logger.v { message }
            }
        }

        level = LogLevel.ALL
    }
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
}