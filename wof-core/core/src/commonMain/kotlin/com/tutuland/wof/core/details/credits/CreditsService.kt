package com.tutuland.wof.core.details.credits

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CreditsService(private val client: HttpClient) : Credits.Api {
    override suspend fun getCreditsFor(id: String): Credits.Result {
        val result: Credits.Result = client.get {
            makeUrlFor("person/$id/combined_credits")
        }.body()
        return result
    }
}
