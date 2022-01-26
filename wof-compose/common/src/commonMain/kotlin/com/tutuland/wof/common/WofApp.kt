package com.tutuland.wof.common

import androidx.compose.runtime.Composable
import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.common.theme.WofTheme
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun WofApp(coroutineScope: CoroutineScope) {
    //TODO: setup navigation routes
    WofTheme {
        TestDetails(coroutineScope)
    }
}

@Composable
fun TestSearch(coroutineScope: CoroutineScope) {
    val viewModel = SearchViewModel(coroutineScope)
    SearchScreen(viewModel)
}

@Composable
fun TestDetails(coroutineScope: CoroutineScope) {
    val detailsCredit = Details.Model.Credit(
        posterUrl = "https://image.tmdb.org/t/p/w300/uxzzxijgPIY7slzFvMotPv8wjKA.jpg",
        title = "Black Panther",
        credit = "Acting",
        year = "2018"
    )
    val detailsModel = Details.Model(
        pictureUrl = "https://image.tmdb.org/t/p/w500/nL16SKfyP1b7Hk6LsuWiqMfbdb8.jpg",
        name = "Chadwick Boseman",
        department = "Director • Screenwriter • Producer",
        bornIn = "Born in November 26, 1976 in Anderson, USA",
        diedIn = "Died in August 28, 2020",
        biography = "Chadwick Boseman was an American actor. He is known for his portrayal of " +
                "T'Challa / Black Panther in the Marvel Cinematic Universe from 2016 to 2019...",
        credits = buildList { repeat(7) { add(detailsCredit) } },
    )
    val viewModel = DetailsViewModel(coroutineScope)
    DetailsScreen(viewModel)
}
