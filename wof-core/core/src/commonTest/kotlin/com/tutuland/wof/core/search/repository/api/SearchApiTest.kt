package com.tutuland.wof.core.search.repository.api

import com.tutuland.wof.core.emptyLogger
import com.tutuland.wof.core.fixSearchRequest
import com.tutuland.wof.core.fixSearchResponse
import com.tutuland.wof.core.fixSearchResult
import com.tutuland.wof.core.fixStringId
import com.tutuland.wof.core.networking.makeHttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class SearchApiTest {
    @Test
    fun when_searchFor_succeeds_return_result() = runTest {
        val engine = MockEngine {
            assertEquals(fixSearchRequest, it.url.toString())
            respond(
                content = fixSearchResponse,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val api = SearchApi.Impl(makeHttpClient(engine, emptyLogger))
        val result = api.searchFor(fixStringId)
        assertEquals(listOf(fixSearchResult), result)
    }

    @Test
    fun when_searchFor_fails_throw_exception() = runTest {
        val engine = MockEngine {
            assertEquals(fixSearchRequest, it.url.toString())
            respondError(
                status = HttpStatusCode.NotFound,
            )
        }
        val api = SearchApi.Impl(makeHttpClient(engine, emptyLogger))
        assertFailsWith<ClientRequestException> { api.searchFor(fixStringId) }
    }
}
