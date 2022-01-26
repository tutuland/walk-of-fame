package com.tutuland.wof.common

import androidx.compose.runtime.Composable
import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.details.FullBioScreen
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias Screen = @Composable () -> Unit

fun <T> MutableList<T>.push(item: T) = add(count(), item)
fun <T> MutableList<T>.pop(): T? = if (count() > 0) removeAt(count() - 1) else null
fun <T> MutableList<T>.peek(): T? = if (count() > 0) this[count() - 1] else null

class WofNavigator(
    scope: CoroutineScope,
    private val exitApp: () -> Unit,
    private val screenStack: MutableList<Screen> = mutableListOf(),
    private val searchViewModel: SearchViewModel = SearchViewModel(scope),
    private val detailsViewModel: DetailsViewModel = DetailsViewModel(scope),
) {
    private val noScreen: Screen = { }
    private val _screenState = MutableStateFlow(noScreen)
    val currentScreen: StateFlow<Screen> = _screenState

    fun goToSearch() {
        navigateTo { SearchScreen(searchViewModel, this) }
    }

    fun goToDetailsFor(id: String) {
        navigateTo { DetailsScreen(detailsViewModel, this) }
        detailsViewModel.requestDetailsWith(id)
    }

    fun goToFullBio() {
        navigateTo { FullBioScreen(detailsViewModel, this) }
    }

    fun goBack() {
        screenStack.pop()
        screenStack.peek()?.let { _screenState.value = it } ?: exitApp()
    }

    private fun navigateTo(screen: Screen) {
        _screenState.value = screen
        screenStack.push(_screenState.value)
    }
}
