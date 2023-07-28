package com.tutuland.wof.common.navigation

import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.details.FullBioScreen
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.core.details.ui.DetailsViewModel
import com.tutuland.wof.core.search.ui.SearchViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NativeNavigator(
    var finishNavigation: () -> Unit,
    private val screenStack: MutableList<Screen> = mutableListOf(),
) : Navigator, KoinComponent {
    private val searchViewModel: SearchViewModel by inject()
    private val detailsViewModel: DetailsViewModel by inject()

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
