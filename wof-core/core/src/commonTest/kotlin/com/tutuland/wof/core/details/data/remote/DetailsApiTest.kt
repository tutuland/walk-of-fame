package com.tutuland.wof.core.details.data.remote

import com.tutuland.wof.core.emptyLogger
import com.tutuland.wof.core.fixCreditsRequest
import com.tutuland.wof.core.fixCreditsResponse
import com.tutuland.wof.core.fixDetailsModel
import com.tutuland.wof.core.fixPersonResponse
import com.tutuland.wof.core.fixPersonRequest
import com.tutuland.wof.core.fixStringId
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.respond
import com.tutuland.wof.core.respondError
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.ClientRequestException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class DetailsApiTest {
    @Test
    fun when_get_person_fails_throw_exception() = runTest {
        val engine = MockEngine {
            respondError()
        }
        val api = DetailsApi.Impl(makeHttpClient(engine, emptyLogger))
        assertFailsWith<ClientRequestException> { api.getDetailsFor(fixStringId) }
    }

    @Test
    fun when_get_credits_fails_throw_exception() = runTest {
        val engine = MockEngine {
            if (it.url.toString() == fixPersonRequest) respond(fixPersonResponse)
            else respondError()
        }
        val api = DetailsApi.Impl(makeHttpClient(engine, emptyLogger))
        assertFailsWith<ClientRequestException> { api.getDetailsFor(fixStringId) }
    }

    @Test
    fun when_both_calls_succeed_return_mapped_model() = runTest {
        val engine = MockEngine {
            if (it.url.toString() == fixCreditsRequest) respond(fixCreditsResponse)
            else respond(fixPersonResponse)
        }
        val api = DetailsApi.Impl(makeHttpClient(engine, emptyLogger))
        val result = api.getDetailsFor(fixStringId)
        assertEquals(fixDetailsModel, result)
    }
}
