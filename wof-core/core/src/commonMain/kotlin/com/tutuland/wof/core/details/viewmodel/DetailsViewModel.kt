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
    private var currentId: String = ""
    private val _state = MutableStateFlow(ViewState(details = initialDetails))
    val viewState: StateFlow<ViewState> = _state

    fun requestDetailsWith(id: String) {
        val details = _state.value.details
        if (currentId == id && details != null) {
            process { details }
        } else {
            currentId = id
            process { requestDetails.with(currentId) }
        }
    }

    fun reloadDetails() {
        requestDetailsWith(currentId)
    }

    private fun process(request: suspend () -> Details.Model) {
        scope.launch {
            startLoading()
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

    private fun startLoading() {
        _state.value = _state.value.copy(isLoading = true, showError = false)
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
        val details: Details.Model? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}
