package com.tutuland.wof.core.search.viewmodel

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val scope: CoroutineScope,
    private val log: Logger = ServiceLocator.log,
    private val searchForPeople: Search.Provider = ServiceLocator.searchForPeople,
    initialResults: List<Search.Model> = listOf(),
) {
    private val _state = MutableStateFlow(ViewState(searchResults = initialResults))
    val viewState: StateFlow<ViewState> = _state

    fun searchFor(personName: String) {
        if (personName.isBlank()) cleanResults()
        else scope.launch {
            startLoadingSearchFor(personName)
            searchForPeople.withName(personName)
                .onEach { model ->
                    displayResult(model)
                }.catch {
                    showError()
                    log.d("searchForPeople failed: $it")
                }.onCompletion {
                    endLoading()
                    if (hasNoResults) showError()
                    log.d("searchForPeople complete.")
                }.collect()
        }
    }

    private fun cleanResults() {
        _state.value = _state.value.copy(searchedTerm = "", searchResults = listOf(), showError = false)
    }

    private fun startLoadingSearchFor(personName: String) {
        _state.value = _state.value.copy(searchedTerm = personName, isLoading = true, showError = false)
    }

    private fun endLoading() {
        _state.value = _state.value.copy(isLoading = false)
    }

    private fun displayResult(result: Search.Model) {
        val searchResults = _state.value.searchResults
        _state.value = _state.value.copy(searchResults = searchResults + result)
    }

    private fun showError() {
        _state.value = _state.value.copy(searchResults = listOf(), showError = true)
    }

    private val hasNoResults: Boolean get() = _state.value.searchResults.isEmpty()

    data class ViewState(
        val searchedTerm: String = "",
        val searchResults: List<Search.Model> = listOf(),
        val showHeader: Boolean = true,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}
