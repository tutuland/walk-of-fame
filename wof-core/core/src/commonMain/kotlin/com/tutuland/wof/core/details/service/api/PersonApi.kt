package com.tutuland.wof.core.details.service.api

import com.tutuland.wof.core.details.service.PersonService
import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface PersonApi : PersonService {
    class Impl(private val client: HttpClient) : PersonApi {
        override suspend fun getPersonFor(id: String): PersonService.Result = client.get {
            makeUrlFor("person/$id")
        }.body()
    }
}
