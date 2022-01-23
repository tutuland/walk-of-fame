package com.tutuland.wof.core.search.api

import com.tutuland.wof.core.networking.makeUrlFor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class SearchApi(private val client: HttpClient) {

    suspend fun searchFor(person: String): Result = client.get {
        makeUrlFor("search/person?query=$person")
    }.body()

    @Serializable
    data class Result(
        @SerialName("results") val people: List<Person>? = null,
    ) {
        val isValid: Boolean get() = people.orEmpty().any { it.isValid }

        @Serializable
        data class Person(
            @SerialName("id") val id: Int? = null,
            @SerialName("name") val name: String? = null,
            @SerialName("known_for") val knownFor: List<Credit>? = null,
            @SerialName("known_for_department") val department: String? = null,
        ) {
            val isValid: Boolean get() = id != null && name.isNullOrEmpty().not()

            @Serializable
            data class Credit(
                @SerialName("id") val id: Int? = null,
            ) {
                val isValid: Boolean get() = id != null
            }
        }
    }
}
