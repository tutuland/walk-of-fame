package com.tutuland.wof.core.details.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("unused")
class NativeDetailsViewModel(
    private val delegate: DetailsViewModel,
    private val scope: CoroutineScope,
) {
    fun onViewState(render: (DetailsViewModel.ViewState) -> Unit) {
        scope.launch { delegate.viewState.collect { render(it) } }
    }

    fun requestDetailsWith(id: String) = delegate.requestDetailsWith(id)
}
