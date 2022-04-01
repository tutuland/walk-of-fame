package com.tutuland.wof.core.search.ui

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.search.data.SearchModel
import com.tutuland.wof.core.search.data.SearchRepository
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
    private val log: Logger,
    private val repository: SearchRepository,
    initialState: ViewState = ViewState(),
) {
    private val _state = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState> = _state

    fun searchFor(personName: String) {
        scope.launch { executeSearchFor(personName) }
    }

    internal suspend fun executeSearchFor(personName: String) {
        cleanResults()
        if (personName.isNotBlank()) {
            startLoadingSearchFor(personName)
            repository.searchFor(personName)
                .onEach { people ->
                    displayResult(people)
                }.catch {
                    showError()
                    log.d("searchForPeople failed: $it")
                }.onCompletion {
                    if (hasNoResults) showError()
                    endLoading()
                    log.d("searchForPeople complete.")
                }.collect()
        }
    }

    private fun cleanResults() {
        _state.value = _state.value.copy(searchedTerm = "", searchResults = listOf(), showError = false)
    }

    private fun startLoadingSearchFor(personName: String) {
        _state.value = _state.value.copy(searchedTerm = personName, isLoading = true)
    }

    private fun endLoading() {
        _state.value = _state.value.copy(isLoading = false)
    }

    private fun displayResult(people: List<SearchModel>) {
        _state.value = _state.value.copy(searchResults = people)
    }

    private fun showError() {
        _state.value = _state.value.copy(searchResults = listOf(), showError = true)
    }

    private val hasNoResults: Boolean get() = _state.value.searchResults.isEmpty()

    data class ViewState(
        val searchedTerm: String = "",
        val searchResults: List<SearchModel> = listOf(),
        val showHeader: Boolean = true,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}
