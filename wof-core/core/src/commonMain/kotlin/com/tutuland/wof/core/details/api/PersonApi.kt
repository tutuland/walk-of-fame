package com.tutuland.wof.core.details.api

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class PersonApi(private val client: HttpClient) {
    suspend fun getPersonFor(id: String): Result = client.get {
        makeUrlFor("person/$id")
    }.body()

    @Serializable
    data class Result(
        @SerialName("id") val id: Int? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("profile_path") val picturePath: String? = null,
        @SerialName("known_for_department") val department: String? = null,
        @SerialName("birthday") val birthday: String? = null,
        @SerialName("deathday") val deathday: String? = null,
        @SerialName("place_of_birth") val placeOfBirth: String? = null,
        @SerialName("biography") val biography: String? = null,
    ) {
        val isValid: Boolean get() = id != null && name.isNullOrEmpty().not()
    }
}
