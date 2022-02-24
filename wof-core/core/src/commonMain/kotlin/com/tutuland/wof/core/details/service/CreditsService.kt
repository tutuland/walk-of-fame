package com.tutuland.wof.core.details.service

import com.tutuland.wof.core.details.service.api.CreditsApi
import com.tutuland.wof.core.details.service.cache.CreditsCache
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface CreditsService {
    suspend fun getCreditsFor(id: String): Result

    @Serializable
    data class Result(
        @SerialName("cast") val cast: List<Work>? = null,
        @SerialName("crew") val crew: List<Work>? = null,
    ) {
        @Serializable
        data class Work(
            @SerialName("id") val id: Int? = null,
            @SerialName("title") val title: String? = null,
            @SerialName("poster_path") val posterPath: String? = null,
            @SerialName("job") private val _job: String? = null,
            @SerialName("character") private val _character: String? = null,
            @SerialName("first_air_date") private val _firstAirDate: String? = null,
            @SerialName("release_date") private val _releaseDate: String? = null,
        ) {
            val isValid: Boolean get() = id != null && title.isNullOrEmpty().not()
            val job: String? get() = _job ?: _character
            val releaseDate: String? get() = _releaseDate ?: _firstAirDate
        }
    }

    class Impl(private val api: CreditsApi, private val cache: CreditsCache) : CreditsService {
        override suspend fun getCreditsFor(id: String): Result {
            return cache.cachedResultsFor(id) ?: api.getCreditsFor(id).also { cache.store(id, it) }
        }
    }
}
