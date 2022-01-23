package com.tutuland.wof.core.details.api

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class CreditsApi(private val client: HttpClient) {
    suspend fun getCreditsFor(id: String): Result = client.get {
        makeUrlFor("person/$id/combined_credits")
    }.body()

    @Serializable
    data class Result(
        @SerialName("cast") val cast: List<Work>? = null,
        @SerialName("crew") val crew: List<Work>? = null,
    ) {
        @Serializable
        data class Work(
            @SerialName("id") val id: Int? = null,
            @SerialName("title") val title: String? = null,
            @SerialName("poster_path") val posterPath: String? = null,
            @SerialName("job") private val _job: String? = null,
            @SerialName("character") private val _character: String? = null,
            @SerialName("first_air_date") private val _firstAirDate: String? = null,
            @SerialName("release_date") private val _releaseDate: String? = null,
        ) {
            val isValid: Boolean get() = id != null && title.isNullOrEmpty().not()
            val job: String? get() = _job ?: _character
            val releaseDate: String? get() = _releaseDate ?: _firstAirDate
        }
    }
}
