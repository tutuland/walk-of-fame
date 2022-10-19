package com.tutuland.wof.core.networking

import com.tutuland.wof.core.BuildKonfig.API_KEY
import com.tutuland.wof.core.BuildKonfig.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

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

fun makeHttpClient(engine: HttpClientEngine, log: KermitLogger) = HttpClient(engine) {
    install(ContentNegotiation) {
        json(
            Json { ignoreUnknownKeys = true }
        )
    }
    install(Logging) {
        logger = object : KtorLogger {
            override fun log(message: String) {
                log.v { message }
            }
        }

        level = LogLevel.INFO
    }
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
}
