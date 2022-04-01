package com.tutuland.wof.core.search.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchPayload(
    @SerialName("results") val people: List<PersonPayload>? = null,
) {
    @Serializable
    data class PersonPayload(
        @SerialName("id") val id: Int? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("known_for_department") val department: String? = null,
    )
}
