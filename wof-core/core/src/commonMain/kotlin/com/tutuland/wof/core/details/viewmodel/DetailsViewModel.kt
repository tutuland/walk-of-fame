package com.tutuland.wof.core.details.viewmodel

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import com.tutuland.wof.core.details.Details
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val scope: CoroutineScope,
    private val log: Logger = ServiceLocator.log,
    private val requestDetails: Details.Provider = ServiceLocator.requestDetails,
    initialDetails: Details.Model? = null,
) {
    private val _state = MutableStateFlow(ViewState(details = initialDetails))
    val viewState: StateFlow<ViewState> = _state

    fun requestDetailsWith(id: String) {
        if (_state.value.id != id) {
            cleanDetails()
        }
        _state.value.details
            ?.let { process(id) { it } }
            ?: process(id) { requestDetails.with(id) }
    }

    private fun process(id: String, request: suspend () -> Details.Model) {
        scope.launch {
            startLoadingWith(id)
            try {
                val model = request()
                displayResult(model)
                log.d("requestDetails succeeded: $model")
            } catch (error: Exception) {
                showError()
                log.d("requestDetails failed: $error")
            }
            endLoading()
        }
    }

    private fun cleanDetails() {
        _state.value = _state.value.copy(id = "", details = null)
    }

    private fun startLoadingWith(id: String) {
        _state.value = _state.value.copy(id = id, isLoading = true, showError = false)
    }

    private fun endLoading() {
        _state.value = _state.value.copy(isLoading = false)
    }

    private fun displayResult(details: Details.Model) {
        _state.value = _state.value.copy(details = details)
    }

    private fun showError() {
        _state.value = _state.value.copy(details = null, showError = true)
    }

    data class ViewState(
        val id: String = "",
        val details: Details.Model? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}
