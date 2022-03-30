package com.tutuland.wof.core.details.repository.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonPayload(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("profile_path") val picturePath: String? = null,
    @SerialName("known_for_department") val department: String? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("deathday") val deathday: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null,
    @SerialName("biography") val biography: String? = null,
)

@Serializable
data class CreditsPayload(
    @SerialName("cast") val cast: List<Work>? = null,
    @SerialName("crew") val crew: List<Work>? = null,
) {
    @Serializable
    data class Work(
        @SerialName("id") val id: Int? = null,
        @SerialName("title") val title: String? = null,
        @SerialName("poster_path") val posterPath: String? = null,
        @SerialName("job") val job: String? = null,
        @SerialName("character") val character: String? = null,
        @SerialName("first_air_date") val firstAirDate: String? = null,
        @SerialName("release_date") val releaseDate: String? = null,
    )
}
