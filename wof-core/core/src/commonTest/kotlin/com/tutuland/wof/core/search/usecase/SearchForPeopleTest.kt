package com.tutuland.wof.core.search.usecase

import app.cash.turbine.test
import com.tutuland.wof.core.fixName
import com.tutuland.wof.core.fixSearchModel
import com.tutuland.wof.core.fixSearchPerson
import com.tutuland.wof.core.search.service.SearchService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.test.runTest

class SearchForPeopleTest {
    var result: SearchService.Result? = null
    private val api = object : SearchService {
        override suspend fun searchFor(person: String): SearchService.Result {
            return result ?: throw Exception()
        }
    }

    @Test
    fun when_api_throws_provider_fails() = runTest {
        result = null
        SearchForPeople(api).withName(fixName).test {
            val result = awaitError()
            assertIs<Exception>(result)
        }
    }

    @Test
    fun when_api_returns_empty_response_provider_fails_with_NoSearchResultsException() = runTest {
        result = SearchService.Result(people = null)
        SearchForPeople(api).withName(fixName).test {
            val result = awaitError()
            assertIs<NoSearchResultsException>(result)
        }
    }

    @Test
    fun when_api_returns_invalid_response_provider_fails_with_NoSearchResultsException() = runTest {
        result = SearchService.Result(people = listOf(fixSearchPerson.copy(id = null)))
        SearchForPeople(api).withName(fixName).test {
            val result = awaitError()
            assertIs<NoSearchResultsException>(result)
        }
    }

    @Test
    fun when_api_returns_valid_response_provider_maps_it_to_model() = runTest {
        result = SearchService.Result(
            people = listOf(
                fixSearchPerson,
                fixSearchPerson.copy(department = "X"),
                fixSearchPerson.copy(department = null),
            )
        )
        SearchForPeople(api).withName(fixName).test {
            assertEquals(fixSearchModel, awaitItem())
            assertEquals(fixSearchModel.copy(department = "Known for X"), awaitItem())
            assertEquals(fixSearchModel.copy(department = ""), awaitItem())
            awaitComplete()
        }
    }
}
