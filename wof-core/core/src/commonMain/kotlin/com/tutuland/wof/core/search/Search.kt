package com.tutuland.wof.core.search

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
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
            @SerialName("name") val name: String? = null,
            @SerialName("profile_path") val picturePath: String? = null,
            @SerialName("known_for") val works: List<Work>? = null,
            @SerialName("known_for_department") val department: String? = null,
        ) {
            @Serializable
            data class Work(
                @JsonNames("name") @SerialName("title") val title: String? = null,
            )
        }
    }
}
