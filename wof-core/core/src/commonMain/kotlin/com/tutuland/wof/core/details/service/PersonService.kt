package com.tutuland.wof.core.details.service

import com.tutuland.wof.core.details.service.api.PersonApi
import com.tutuland.wof.core.details.service.cache.PersonCache
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface PersonService {
    suspend fun getPersonFor(id: String): Result

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

    class Impl(private val api: PersonApi, private val cache: PersonCache) : PersonService {
        override suspend fun getPersonFor(id: String): Result {
            return cache.cachedResultsFor(id) ?: api.getPersonFor(id).also { cache.store(id, it) }
        }
    }
}
