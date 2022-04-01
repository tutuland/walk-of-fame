package com.tutuland.wof.core.details.ui

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.details.data.DetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val scope: CoroutineScope,
    private val log: Logger,
    private val repository: DetailsRepository,
    initialState: ViewState = ViewState(),
) {
    private val _state = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState> = _state

    fun requestDetailsWith(id: String) {
        scope.launch { executeRequestDetailsWith(id) }
    }

    internal suspend fun executeRequestDetailsWith(id: String) {
        if (_state.value.id != id) cleanDetails()
        startLoadingWith(id)
        try {
            val model = _state.value.details ?: repository.getDetailsFor(id)
            displayResult(model)
            log.d("requestDetails complete.")
        } catch (error: Exception) {
            showError()
            log.d("requestDetails failed: $error")
        }
        endLoading()
    }

    private fun cleanDetails() {
        _state.value = _state.value.copy(id = "", details = null, showError = false)
    }

    private fun startLoadingWith(id: String) {
        _state.value = _state.value.copy(id = id, isLoading = true, showError = false)
    }

    private fun endLoading() {
        _state.value = _state.value.copy(isLoading = false)
    }

    private fun displayResult(details: DetailsModel) {
        _state.value = _state.value.copy(details = details)
    }

    private fun showError() {
        _state.value = _state.value.copy(details = null, showError = true)
    }

    data class ViewState(
        val id: String = "",
        val details: DetailsModel? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}
