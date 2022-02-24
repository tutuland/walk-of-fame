package com.tutuland.wof.core.details.service.api

import com.tutuland.wof.core.details.service.CreditsService
import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CreditsApi : CreditsService {
    class Impl(private val client: HttpClient) : CreditsApi {
        override suspend fun getCreditsFor(id: String): CreditsService.Result = client.get {
            makeUrlFor("person/$id/combined_credits")
        }.body()
    }
}
