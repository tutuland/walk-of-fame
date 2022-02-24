package com.tutuland.wof.core.search.service

import com.tutuland.wof.core.search.service.api.SearchApi
import com.tutuland.wof.core.search.service.cache.SearchCache
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface SearchService {
    suspend fun searchFor(person: String): Result

    @Serializable
    data class Result(
        @SerialName("results") val people: List<Person>? = null,
    ) {
        val isValid: Boolean get() = people.orEmpty().any { it.isValid }

        @Serializable
        data class Person(
            @SerialName("id") val id: Int? = null,
            @SerialName("name") val name: String? = null,
            @SerialName("known_for_department") val department: String? = null,
        ) {
            val isValid: Boolean get() = id != null && name.isNullOrEmpty().not()
        }
    }

    class Impl(private val api: SearchApi, private val cache: SearchCache) : SearchService {
        override suspend fun searchFor(person: String): Result {
            val cachedResults = cache.cachedResultsFor(person)
            return if (cachedResults.isValid) cachedResults
            else api.searchFor(person).also { result -> cache.store(person, result) }
        }
    }
}
