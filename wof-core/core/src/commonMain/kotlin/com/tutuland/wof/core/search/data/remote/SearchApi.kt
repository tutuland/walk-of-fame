package com.tutuland.wof.core.search.data.remote

import com.tutuland.wof.core.networking.makeUrlFor
import com.tutuland.wof.core.search.data.SearchModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface SearchApi {
    suspend fun searchFor(person: String): List<SearchModel>

    class Impl(private val client: HttpClient) : SearchApi {
        override suspend fun searchFor(person: String): List<SearchModel> {
            val response: SearchPayload = client
                .get { makeUrlFor("search/person?query=$person") }
                .body()
            return response.mapToResults()
        }
    }
}
