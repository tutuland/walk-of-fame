package com.tutuland.wof.core.details.api

import com.tutuland.wof.core.emptyLogger
import com.tutuland.wof.core.fixPersonApiResult
import com.tutuland.wof.core.fixPersonPayload
import com.tutuland.wof.core.fixPersonRequest
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

class PersonApiTest {
    @Test
    fun when_getPersonFor_succeeds_return_result() = runTest {
        val engine = MockEngine {
            assertEquals(fixPersonRequest, it.url.toString())
            respond(
                content = fixPersonPayload,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val api = PersonApi.Impl(makeHttpClient(engine, emptyLogger))
        val result = api.getPersonFor(fixStringId)
        assertEquals(fixPersonApiResult, result)
    }

    @Test
    fun when_getPersonFor_fails_throw_exception() = runTest {
        val engine = MockEngine {
            assertEquals(fixPersonRequest, it.url.toString())
            respond(
                content = "",
                status = HttpStatusCode.NotFound,
            )
        }
        val api = PersonApi.Impl(makeHttpClient(engine, emptyLogger))
        assertFailsWith<ClientRequestException> { api.getPersonFor(fixStringId) }
    }
}
