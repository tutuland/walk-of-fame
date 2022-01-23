package com.tutuland.wof.core.details.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Credits {
    interface Api {
        suspend fun getCreditsFor(id: String): Result
    }

    @Serializable
    data class Result(
        @SerialName("cast") val cast: List<Cast>? = null,
        @SerialName("crew") val crew: List<Crew>? = null,
    ) {
        @Serializable
        data class Cast(
            @SerialName("title") val title: String? = null,
            @SerialName("poster_path") val posterPath: String? = null,
            @SerialName("character") val character: String? = null,
            @SerialName("release_date") val releaseDate: String? = null,
        )

        @Serializable
        data class Crew(
            @SerialName("title") val title: String? = null,
            @SerialName("poster_path") val posterPath: String? = null,
            @SerialName("job") val job: String? = null,
            @SerialName("release_date") val releaseDate: String? = null,
        )
    }
}