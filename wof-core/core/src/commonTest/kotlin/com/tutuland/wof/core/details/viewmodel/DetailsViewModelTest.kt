package com.tutuland.wof.core.details.viewmodel

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.fixDetailsModel
import com.tutuland.wof.core.fixStringId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class DetailsViewModelTest {
    private val lastId = "last id"
    private val lastModel = fixDetailsModel.copy(name = "Last Model")
    private val scope: CoroutineScope = TestScope()
    private val log: Logger = Logger(StaticConfig(logWriterList = listOf()))
    private val requestDetails = object : Details.Provider {
        override suspend fun with(id: String): Details.Model = result ?: throw Exception()
    }
    private var result: Details.Model? = null
    private lateinit var viewModel: DetailsViewModel
    private lateinit var currState: DetailsViewModel.ViewState

    @Test
    fun when_id_equals_last_id_return_cached_model() = runTest {
        result = null
        viewModel = DetailsViewModel(scope, log, requestDetails, initialState())
        viewModel.viewState.test {
            viewModel.executeRequestDetailsWith(lastId)
            expect(initialState())
            expect(startLoadingWith(lastId))
            // displayResult is not emitted by StateFlow because it is identical to last state
            expect(endLoading())
            expectNoEvents()
        }
    }

    @Test
    fun when_requestDetails_throws_showError() = runTest {
        result = null
        viewModel = DetailsViewModel(scope, log, requestDetails, initialState())
        viewModel.viewState.test {
            viewModel.executeRequestDetailsWith(fixStringId)
            expect(initialState())
            expect(cleanDetails())
            expect(startLoadingWith(fixStringId))
            expect(showError())
            expect(endLoading())
            expectNoEvents()
        }
    }

    @Test
    fun when_requestDetails_returns_a_model_displayResult() = runTest {
        result = fixDetailsModel
        viewModel = DetailsViewModel(scope, log, requestDetails, initialState())
        viewModel.viewState.test {
            viewModel.executeRequestDetailsWith(fixStringId)
            expect(initialState())
            expect(cleanDetails())
            expect(startLoadingWith(fixStringId))
            expect(displayResult(fixDetailsModel))
            expect(endLoading())
            expectNoEvents()
        }
    }

    private fun initialState() = DetailsViewModel.ViewState(lastId, lastModel)
    private fun cleanDetails() = currState.copy(id = "", details = null, showError = false)
    private fun startLoadingWith(id: String) = currState.copy(id = id, isLoading = true, showError = false)
    private fun endLoading() = currState.copy(isLoading = false)
    private fun displayResult(details: Details.Model) = currState.copy(details = details)
    private fun showError() = currState.copy(details = null, showError = true)
    private suspend fun FlowTurbine<DetailsViewModel.ViewState>.expect(expectedState: DetailsViewModel.ViewState) {
        currState = awaitItem()
        assertEquals(expectedState, currState)
    }
}
