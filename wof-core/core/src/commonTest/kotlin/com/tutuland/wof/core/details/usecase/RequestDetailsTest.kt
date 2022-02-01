package com.tutuland.wof.core.details.usecase

import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.api.CreditsApi
import com.tutuland.wof.core.details.api.PersonApi
import com.tutuland.wof.core.fixBirthday
import com.tutuland.wof.core.fixBornInUnknown
import com.tutuland.wof.core.fixCreditsApiCast
import com.tutuland.wof.core.fixCreditsApiCrew
import com.tutuland.wof.core.fixCreditsApiResult
import com.tutuland.wof.core.fixDetailsModel
import com.tutuland.wof.core.fixId
import com.tutuland.wof.core.fixName
import com.tutuland.wof.core.fixPersonApiResult
import com.tutuland.wof.core.fixStringId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

private object PersonApiFailed : Exception()
private object CreditsApiFailed : Exception()

class RequestDetailsTest {
    var personResult: PersonApi.Result? = null
    private val personApi = object : PersonApi {
        override suspend fun getPersonFor(id: String): PersonApi.Result {
            return personResult ?: throw PersonApiFailed
        }
    }
    var creditsResult: CreditsApi.Result? = null
    private val creditsApi = object : CreditsApi {
        override suspend fun getCreditsFor(id: String): CreditsApi.Result {
            return creditsResult ?: throw CreditsApiFailed
        }
    }

    @Test
    fun when_personApi_throws_provider_fails() = runTest {
        personResult = null
        creditsResult = fixCreditsApiResult

        assertFailsWith<PersonApiFailed> { RequestDetails(personApi, creditsApi).with(fixStringId) }
    }

    @Test
    fun when_personApi_returns_invalid_response_provider_fails_with_InvalidPersonResult() = runTest {
        personResult = fixPersonApiResult.copy(id = null)
        creditsResult = fixCreditsApiResult

        assertFailsWith<InvalidPersonResult> { RequestDetails(personApi, creditsApi).with(fixStringId) }
    }

    @Test
    fun when_creditsResult_throws_provider_fails() = runTest {
        personResult = fixPersonApiResult
        creditsResult = null

        assertFailsWith<CreditsApiFailed> { RequestDetails(personApi, creditsApi).with(fixStringId) }
    }

    @Test
    fun when_apis_return_valid_responses_provider_maps_them_to_model() = runTest {
        personResult = fixPersonApiResult
        creditsResult = fixCreditsApiResult

        val model = RequestDetails(personApi, creditsApi).with(fixStringId)

        assertEquals(fixDetailsModel, model)
    }

    @Test
    fun when_apis_return_incomplete_responses_provider_maps_them_to_model() = runTest {
        personResult = PersonApi.Result(
            id = fixId,
            name = fixName,
            picturePath = null,
            department = null,
            birthday = fixBirthday,
            deathday = null,
            placeOfBirth = null,
            biography = null,
        )
        creditsResult = CreditsApi.Result(
            cast = listOf(fixCreditsApiCast.copy(id = null)),
            crew = listOf(fixCreditsApiCrew.copy(title = null)),
        )
        val expected = Details.Model(
            pictureUrl = "",
            name = fixName,
            department = "",
            bornIn = fixBornInUnknown,
            diedIn = "",
            biography = "",
            credits = listOf(),
        )

        val model = RequestDetails(personApi, creditsApi).with(fixStringId)

        assertEquals(expected, model)
    }
}
