package com.tutuland.wof.core.details.person

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PersonService(private val client: HttpClient) : Person.Api {
    override suspend fun getPersonFor(id: String): Person.Result {
        val result: Person.Result = client.get {
            makeUrlFor("person/$id")
        }.body()
        return result
    }
}
