package com.tutuland.wof.core.details

import com.tutuland.wof.core.details.credits.Credits
import com.tutuland.wof.core.details.person.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Details {
    interface Api {
        suspend fun getDetailsFor(id: String): Result
    }

    @Serializable
    data class Result(
        @SerialName("person") val person: Person.Result,
        @SerialName("credits") val credits: Credits.Result,
    )
}

