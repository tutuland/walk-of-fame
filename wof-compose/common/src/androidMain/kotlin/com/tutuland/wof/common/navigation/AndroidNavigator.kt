package com.tutuland.wof.common.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentScope.SlideDirection
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Right
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.details.FullBioScreen
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.core.details.ui.DetailsViewModel
import com.tutuland.wof.core.search.ui.SearchViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidNavigator(
    private val navController: NavHostController,
) : Navigator, KoinComponent {
    private val searchViewModel: SearchViewModel by inject()
    private val detailsViewModel: DetailsViewModel by inject()
    private val initialScreen: Screen = {
        AndroidNavHost(
            navController = navController,
            searchScreen = { SearchScreen(searchViewModel, this) },
            detailsScreen = { id -> DetailsScreen(id, detailsViewModel, this) },
            fullBioScreen = { FullBioScreen(detailsViewModel, this) },
        )
    }
    private val _screenState = MutableStateFlow(initialScreen)
    override val currentScreen: StateFlow<Screen> = _screenState

    override fun goToSearch() {
        navController.navigate(Routes.search)
    }

    override fun goToDetailsFor(id: String) {
        navController.navigate(Routes.details(id))
        detailsViewModel.requestDetailsWith(id)
    }

    override fun goToFullBio() {
        navController.navigate(Routes.fullbio)
    }

    override fun goBack(): Boolean = navController.navigateUp()
}

object Routes {
    const val search = "search"
    fun details(id: String) = "details/$id"
    const val fullbio = "fullbio"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AndroidNavHost(
    navController: NavHostController,
    searchScreen: @Composable () -> Unit,
    detailsScreen: @Composable (String) -> Unit,
    fullBioScreen: @Composable () -> Unit,
) {
    val duration = 500
    fun AnimatedContentScope<NavBackStackEntry>.slideIn(towards: SlideDirection) =
        slideIntoContainer(towards, animationSpec = tween(duration))

    fun AnimatedContentScope<NavBackStackEntry>.slideOut(towards: SlideDirection) =
        slideOutOfContainer(towards, animationSpec = tween(duration))

    AnimatedNavHost(navController, startDestination = Routes.search) {
        composable(
            route = Routes.search,
            enterTransition = { slideIn(Left) },
            exitTransition = { slideOut(Left) },
            popEnterTransition = { slideIn(Right) },
            popExitTransition = { slideOut(Right) },
        ) {
            searchScreen()
        }
        composable(
            route = Routes.details("{id}"),
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            enterTransition = { slideIn(Left) },
            exitTransition = { slideOut(Left) },
            popEnterTransition = { slideIn(Right) },
            popExitTransition = { slideOut(Right) },
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            detailsScreen(id)
        }
        composable(
            route = Routes.fullbio,
            enterTransition = { slideIn(Left) },
            exitTransition = { slideOut(Left) },
            popEnterTransition = { slideIn(Right) },
            popExitTransition = { slideOut(Right) },
        ) {
            fullBioScreen()
        }
    }
}
