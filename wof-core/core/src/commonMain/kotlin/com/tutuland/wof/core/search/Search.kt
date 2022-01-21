package com.tutuland.wof.core.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Search {
    interface Api {
        suspend fun searchFor(person: String): Result
    }

    @Serializable
    data class Result(
        @SerialName("results") val people: List<Person>? = null,
    ) {
        @Serializable
        data class Person(
            @SerialName("id") val id: Int? = null,
            @SerialName("name") val name: String? = null,
            @SerialName("profile_path") val picturePath: String? = null,
            @SerialName("known_for_department") val department: String? = null,
        )
    }
}
