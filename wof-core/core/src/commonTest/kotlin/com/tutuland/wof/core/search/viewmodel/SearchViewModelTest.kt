package com.tutuland.wof.core.search.viewmodel

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.tutuland.wof.core.fixName
import com.tutuland.wof.core.fixSearchModel
import com.tutuland.wof.core.search.Search
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
    private val searchForPeople = object : Search.Provider {
        override fun withName(name: String): Flow<Search.Model> = resultFlow
    }
    private lateinit var resultFlow: Flow<Search.Model>
    private lateinit var viewModel: SearchViewModel
    private lateinit var currState: SearchViewModel.ViewState

    @Test
    fun when_personName_is_null_only_cleanResults() = runTest {
        resultFlow = flow { }
        viewModel = SearchViewModel(scope, log, searchForPeople, initialState())
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
        viewModel = SearchViewModel(scope, log, searchForPeople, initialState())
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
        viewModel = SearchViewModel(scope, log, searchForPeople, initialState())
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
        resultFlow = flowOf(fixSearchModel.copy(id = "3"), fixSearchModel.copy(id = "6"), fixSearchModel.copy(id = "9"))
        viewModel = SearchViewModel(scope, log, searchForPeople, initialState())
        viewModel.viewState.test {
            viewModel.executeSearchFor(fixName)
            expect(initialState())
            expect(cleanResults())
            expect(startLoadingSearchFor(fixName))
            expect(displayResult(fixSearchModel.copy(id = "3")))
            expect(displayResult(fixSearchModel.copy(id = "6")))
            expect(displayResult(fixSearchModel.copy(id = "9")))
            expect(endLoading())
            expectNoEvents()
        }
    }

    private fun initialState() = SearchViewModel.ViewState("last term")
    private fun cleanResults() = currState.copy(searchedTerm = "", searchResults = listOf(), showError = false)
    private fun startLoadingSearchFor(personName: String) = currState.copy(searchedTerm = personName, isLoading = true)
    private fun endLoading() = currState.copy(isLoading = false)
    private fun displayResult(result: Search.Model) = currState.copy(searchResults = currState.searchResults + result)
    private fun showError() = currState.copy(searchResults = listOf(), showError = true)
    private suspend fun FlowTurbine<SearchViewModel.ViewState>.expect(expectedState: SearchViewModel.ViewState) {
        currState = awaitItem()
        assertEquals(expectedState, currState)
    }
}
