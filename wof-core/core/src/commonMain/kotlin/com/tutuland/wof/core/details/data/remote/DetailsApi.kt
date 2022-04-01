package com.tutuland.wof.core.details.data.remote

import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface DetailsApi {
    suspend fun getDetailsFor(id: String): DetailsModel

    class Impl(private val client: HttpClient) : DetailsApi {
        override suspend fun getDetailsFor(id: String): DetailsModel {
            val person: PersonPayload = client
                .get { makeUrlFor("person/$id") }
                .body()
            val credits: CreditsPayload = client
                .get { makeUrlFor("person/$id/combined_credits") }
                .body()
            return person mapToDetailsWith credits
        }
    }
}
