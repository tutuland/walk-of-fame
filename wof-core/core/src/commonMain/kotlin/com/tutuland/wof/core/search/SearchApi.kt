package com.tutuland.wof.core.search

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SearchApi(private val client: HttpClient) : Search.Api {

    override suspend fun searchFor(person: String): String {
        val result: String = client.get {
            makeUrlFor("search/person?query=$person")
        }.body()
        return result
    }
}
