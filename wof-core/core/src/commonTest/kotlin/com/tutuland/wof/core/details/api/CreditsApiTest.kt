package com.tutuland.wof.core.details.api

import com.tutuland.wof.core.emptyLogger
import com.tutuland.wof.core.fixCreditsApiResult
import com.tutuland.wof.core.fixCreditsPayload
import com.tutuland.wof.core.fixCreditsRequest
import com.tutuland.wof.core.fixStringId
import com.tutuland.wof.core.networking.makeHttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class CreditsApiTest {
    @Test
    fun when_getCreditsFor_succeeds_return_result() = runTest {
        val engine = MockEngine {
            assertEquals(fixCreditsRequest, it.url.toString())
            respond(
                content = fixCreditsPayload,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val api = CreditsApi.Impl(makeHttpClient(engine, emptyLogger))
        val result = api.getCreditsFor(fixStringId)
        assertEquals(fixCreditsApiResult, result)
    }

    @Test
    fun when_getCreditsFor_fails_throw_exception() = runTest {
        val engine = MockEngine {
            assertEquals(fixCreditsRequest, it.url.toString())
            respond(
                content = "",
                status = HttpStatusCode.NotFound,
            )
        }
        val api = CreditsApi.Impl(makeHttpClient(engine, emptyLogger))
        assertFailsWith<ClientRequestException> { api.getCreditsFor(fixStringId) }
    }
}
