package com.tutuland.wof.core.search.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("unused")
class NativeSearchViewModel(
    private val delegate: SearchViewModel,
    private val scope: CoroutineScope,
) {
    fun onViewState(render: (SearchViewModel.ViewState) -> Unit) {
        scope.launch { delegate.viewState.collect { render(it) } }
    }

    fun searchFor(personName: String) = delegate.searchFor(personName)
}
