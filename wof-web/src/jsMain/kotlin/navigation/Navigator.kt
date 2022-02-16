package navigation

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import details.DetailsScreen
import details.FullBioScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import search.SearchScreen

typealias Screen = @Composable () -> Unit

fun <T> MutableList<T>.push(item: T) = add(count(), item)
fun <T> MutableList<T>.pop(): T? = if (isNotEmpty()) removeAt(count() - 1) else null
fun <T> MutableList<T>.peek(): T? = if (isNotEmpty()) this[count() - 1] else null

interface Navigator {
    val currentScreen: StateFlow<Screen>
    fun goToSearch()
    fun goToDetailsFor(id: String)
    fun goToFullBio()
    fun goBack(): Boolean
}

@OptIn(DelicateCoroutinesApi::class)
class WebNavigator : Navigator, KoinComponent {
    private val log: Logger by inject { parametersOf("WoF WebNavigator") }
    private val searchViewModel: SearchViewModel by inject()
    private val detailsViewModel: DetailsViewModel by inject()
    private val screenStack: MutableList<Screen> = mutableListOf()
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
        if (screenStack.count() > 1) screenStack.pop()
        screenStack.peek()?.let { _screenState.value = it } ?: log.d("Navigation reached root")
        return true
    }

    private fun navigateTo(screen: Screen) {
        _screenState.value = screen
        screenStack.push(_screenState.value)
    }
}
