package com.tutuland.wof.core.search.service.api

import com.tutuland.wof.core.networking.makeUrlFor
import com.tutuland.wof.core.search.service.SearchService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface SearchApi : SearchService {
    class Impl(private val client: HttpClient) : SearchApi {
        override suspend fun searchFor(person: String): SearchService.Result = client.get {
            makeUrlFor("search/person?query=$person")
        }.body()
    }
}
