package com.tutuland.wof.core.details.data

import com.tutuland.wof.core.details.data.remote.DetailsApi
import com.tutuland.wof.core.details.data.local.DetailsCache
import com.tutuland.wof.core.fixDetailsModel
import com.tutuland.wof.core.fixStringId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class DetailsRepositoryTest {
    private object ApiException : Exception()
    private object CacheException : Exception()

    private val api = object : DetailsApi {
        var result: DetailsModel? = null
        override suspend fun getDetailsFor(id: String): DetailsModel {
            return result ?: throw ApiException
        }
    }

    private val cache = object : DetailsCache {
        var result: DetailsModel? = null
        override suspend fun getDetailsFor(id: String): DetailsModel? {
            return if (id == fixStringId) result else throw CacheException
        }

        override suspend fun store(id: String, details: DetailsModel) {
            this.result = details
        }
    }

    private val repository = DetailsRepository.Impl(api, cache)

    @Test
    fun when_cache_throws_getDetailsFor_fails() = runTest {
        cache.result = null
        assertFailsWith<CacheException> { repository.getDetailsFor("id that throws") }
    }

    @Test
    fun when_api_throws_getDetailsFor_fails() = runTest {
        cache.result = null
        api.result = null
        assertFailsWith<ApiException> { repository.getDetailsFor(fixStringId) }
    }

    @Test
    fun when_cache_returns_results_return_them() = runTest {
        cache.result = fixDetailsModel
        api.result = null
        val result = repository.getDetailsFor(fixStringId)
        assertEquals(fixDetailsModel, result)
    }

    @Test
    fun when_cache_returns_nothing_but_api_returns_results_cache_and_emit_them() = runTest {
        cache.result = null
        api.result = fixDetailsModel
        val result = repository.getDetailsFor(fixStringId)
        assertEquals(fixDetailsModel, result)
        assertEquals(fixDetailsModel, cache.result)
    }
}
