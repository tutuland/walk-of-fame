package com.tutuland.wof.core.search.viewmodel

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.tutuland.wof.core.fixName
import com.tutuland.wof.core.fixSearchResult
import com.tutuland.wof.core.search.repository.SearchModel
import com.tutuland.wof.core.search.repository.SearchRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class SearchViewModelTest {
    private val scope: CoroutineScope = TestScope()
    private val log: Logger = Logger(StaticConfig(logWriterList = listOf()))
    private val repository = object : SearchRepository {
        override fun searchFor(name: String): Flow<List<SearchModel>> = resultFlow
    }
    private lateinit var resultFlow: Flow<List<SearchModel>>
    private lateinit var viewModel: SearchViewModel
    private lateinit var currState: SearchViewModel.ViewState

    @Test
    fun when_personName_is_null_only_cleanResults() = runTest {
        resultFlow = flow { }
        viewModel = SearchViewModel(scope, log, repository, initialState())
        viewModel.viewState.test {
            viewModel.executeSearchFor("")
            expect(initialState())
            expect(cleanResults())
            expectNoEvents()
        }
    }

    @Test
    fun when_searchForPeople_throws_showError() = runTest {
        resultFlow = flow { throw Exception() }
        viewModel = SearchViewModel(scope, log, repository, initialState())
        viewModel.viewState.test {
            viewModel.executeSearchFor(fixName)
            expect(initialState())
            expect(cleanResults())
            expect(startLoadingSearchFor(fixName))
            expect(showError())
            expect(endLoading())
            expectNoEvents()
        }
    }

    @Test
    fun when_searchForPeople_returns_nothing_showError() = runTest {
        resultFlow = flowOf()
        viewModel = SearchViewModel(scope, log, repository, initialState())
        viewModel.viewState.test {
            viewModel.executeSearchFor(fixName)
            expect(initialState())
            expect(cleanResults())
            expect(startLoadingSearchFor(fixName))
            expect(showError())
            expect(endLoading())
            expectNoEvents()
        }
    }

    @Test
    fun when_searchForPeople_returns_models_displayResults() = runTest {
        val people = listOf(
            fixSearchResult.copy(id = "3"),
            fixSearchResult.copy(id = "6"),
            fixSearchResult.copy(id = "9"),
        )
        resultFlow = flowOf(people)
        viewModel = SearchViewModel(scope, log, repository, initialState())
        viewModel.viewState.test {
            viewModel.executeSearchFor(fixName)
            expect(initialState())
            expect(cleanResults())
            expect(startLoadingSearchFor(fixName))
            expect(displayResult(people))
            expect(endLoading())
            expectNoEvents()
        }
    }

    private fun initialState() = SearchViewModel.ViewState("last term")
    private fun cleanResults() = currState.copy(searchedTerm = "", searchResults = listOf(), showError = false)
    private fun startLoadingSearchFor(personName: String) = currState.copy(searchedTerm = personName, isLoading = true)
    private fun endLoading() = currState.copy(isLoading = false)
    private fun displayResult(people: List<SearchModel>) = currState.copy(searchResults = people)
    private fun showError() = currState.copy(searchResults = listOf(), showError = true)
    private suspend fun FlowTurbine<SearchViewModel.ViewState>.expect(expectedState: SearchViewModel.ViewState) {
        currState = awaitItem()
        assertEquals(expectedState, currState)
    }
}
