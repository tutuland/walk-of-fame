package com.tutuland.wof.core.search.data

import app.cash.turbine.test
import com.tutuland.wof.core.fixName
import com.tutuland.wof.core.fixSearchResult
import com.tutuland.wof.core.search.data.remote.SearchApi
import com.tutuland.wof.core.search.data.local.SearchCache
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.test.runTest

class SearchRepositoryTest {
    private object ApiException : Exception()
    private object CacheException : Exception()

    private val api = object : SearchApi {
        var results: List<SearchModel>? = null

        override suspend fun searchFor(person: String): List<SearchModel> {
            return results ?: throw ApiException
        }
    }

    private val cache = object : SearchCache {
        var results: List<SearchModel>? = null

        override suspend fun cachedResultsFor(term: String): List<SearchModel> {
            return results ?: throw CacheException
        }

        override suspend fun store(term: String, results: List<SearchModel>) {
            this.results = results
        }
    }

    private val repository = SearchRepository.Impl(api, cache)

    @Test
    fun when_cache_throws_searchFor_fails() = runTest {
        cache.results = null
        repository.searchFor(fixName).test {
            assertIs<CacheException>(awaitError())
        }
    }

    @Test
    fun when_api_throws_searchFor_fails() = runTest {
        cache.results = listOf()
        api.results = null
        repository.searchFor(fixName).test {
            assertIs<ApiException>(awaitError())
        }
    }

    @Test
    fun when_neither_the_cache_nor_the_api_return_results_searchFor_fails() = runTest {
        cache.results = listOf()
        api.results = listOf()
        repository.searchFor(fixName).test {
            assertIs<NoSearchResultsException>(awaitError())
        }
    }

    @Test
    fun when_cache_returns_results_emit_them() = runTest {
        val cacheResults = listOf(fixSearchResult)
        cache.results = cacheResults
        api.results = listOf()
        repository.searchFor(fixName).test {
            assertEquals(cacheResults, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun when_cache_returns_nothing_but_api_returns_results_cache_and_emit_them() = runTest {
        val apiResults = listOf(fixSearchResult)
        cache.results = listOf()
        api.results = apiResults
        repository.searchFor(fixName).test {
            assertEquals(apiResults, awaitItem())
            assertEquals(apiResults, cache.results)
            awaitComplete()
        }
    }
}
