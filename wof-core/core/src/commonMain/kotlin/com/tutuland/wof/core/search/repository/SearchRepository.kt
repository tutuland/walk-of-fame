package com.tutuland.wof.core.search.repository

import com.tutuland.wof.core.search.repository.api.SearchApi
import com.tutuland.wof.core.search.repository.cache.SearchCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SearchRepository {
    fun searchFor(name: String): Flow<List<SearchModel>>

    class Impl(
        private val api: SearchApi,
        private val cache: SearchCache,
    ) : SearchRepository {
        override fun searchFor(name: String): Flow<List<SearchModel>> = flow {
            val people = getPeopleWith(name)
            if (people.isEmpty()) throw NoSearchResultsException(name)
            emit(people)
        }

        private suspend inline fun getPeopleWith(name: String): List<SearchModel> {
            return cache.cachedResultsFor(name)
                .ifEmpty { fetchPeopleAndAddToCache(name) }
        }

        private suspend fun fetchPeopleAndAddToCache(name: String): List<SearchModel> {
            val results = api.searchFor(name)
            cache.store(name, results)
            return results
        }
    }
}

class NoSearchResultsException(name: String) : Exception("Search for person failed for: $name")
