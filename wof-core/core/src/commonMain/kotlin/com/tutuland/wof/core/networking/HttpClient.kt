package com.tutuland.wof.core.networking

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json

private const val BASE_URL = "https://api.themoviedb.org/3/"
// TODO: Never commit api keys! Look for a dynamic solution
// maybe https://github.com/yshrsmz/BuildKonfig
private const val API_KEY = "api_key="

fun HttpRequestBuilder.makeUrlFor(path: String) {
    val cleanPath = path.dropWhile { it == '/' }.replace(" ", "%20")
    val jointWith = if (cleanPath.contains('?')) '&' else '?'
    val finalUrl = buildString {
        append(BASE_URL)
        append(cleanPath)
        append(jointWith)
        append(API_KEY)
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