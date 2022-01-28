package com.tutuland.wof.common.navigation

import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.details.FullBioScreen
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DesktopNavigator(
    scope: CoroutineScope,
    var finishNavigation: () -> Unit,
    private val screenStack: MutableList<Screen> = mutableListOf(),
    private val searchViewModel: SearchViewModel = SearchViewModel(scope),
    private val detailsViewModel: DetailsViewModel = DetailsViewModel(scope),
) : Navigator {
    private val noScreen: Screen = { }
    private val _screenState = MutableStateFlow(noScreen)
    override val currentScreen: StateFlow<Screen> = _screenState

    init {
        goToSearch()
    }

    override fun goToSearch() {
        navigateTo { SearchScreen(searchViewModel, this) }
    }

    override fun goToDetailsFor(id: String) {
        detailsViewModel.requestDetailsWith(id)
        navigateTo { DetailsScreen(id, detailsViewModel, this) }
    }

    override fun goToFullBio() {
        navigateTo { FullBioScreen(detailsViewModel, this) }
    }

    override fun goBack(): Boolean {
        screenStack.pop()
        screenStack.peek()?.let { _screenState.value = it } ?: finishNavigation()
        return true
    }

    private fun navigateTo(screen: Screen) {
        _screenState.value = screen
        screenStack.push(_screenState.value)
    }
}
