package com.tutuland.wof.core.details.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Person {
    interface Api {
        suspend fun getPersonFor(id: String): Result
    }

    @Serializable
    data class Result(
        @SerialName("name") val name: String? = null,
        @SerialName("profile_path") val picturePath: String? = null,
        @SerialName("known_for_department") val department: String? = null,
        @SerialName("birthday") val birthday: String? = null,
        @SerialName("deathday") val deathday: String? = null,
        @SerialName("place_of_birth") val placeOfBirth: String? = null,
        @SerialName("biography") val biography: String? = null,
    )
}