package com.tutuland.wof.common

import androidx.compose.runtime.Composable
import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.details.FullBioScreen
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
    val viewModel = DetailsViewModel(coroutineScope)
    DetailsScreen(viewModel)
}

@Composable
fun TestFullBio(coroutineScope: CoroutineScope) {
    val viewModel = DetailsViewModel(coroutineScope, initialDetails = detailsModel)
    FullBioScreen(viewModel)
}

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
    biography = "Chadwick Boseman was an American actor, playwright, and screenwriter hailing from " +
            "Anderson, South Carolina. He graduated from Howard University and went on to study at " +
            "the British American Dramatic Academy in Oxford.  Boseman's play \"Deep Azure\" was" +
            " nominated for a 2006 Joseph Jefferson Award for New Work.  His breakout role was playing" +
            " the lead Jackie Robinson in 2013's 42.\n\nBoseman was best remembered for portraying" +
            " T’Challa/Black Panther in the Marvel Cinematic Universe. He has portrayed the character" +
            " in Captain America: Civil War (2016), Black Panther (2018), Avengers: Infinity War (2018)," +
            " and Avengers: Endgame (2019). \n\nChadwick Boseman was an American actor, playwright, and " +
            "screenwriter hailing from Anderson, South Carolina. He graduated from Howard University and " +
            "went on to study at the British American Dramatic Academy in Oxford.  Boseman's play " +
            "\"Deep Azure\" was nominated for a 2006 Joseph Jefferson Award for New Work.  " +
            "His breakout role was playing the lead Jackie Robinson in 2013's 42.\n\nBoseman was best " +
            "remembered for portraying T’Challa/Black Panther in the Marvel Cinematic Universe. He has " +
            "portrayed the character in Captain America: Civil War (2016), Black Panther (2018), Avengers: " +
            "Infinity War (2018), and Avengers: Endgame (2019).",
    credits = buildList { repeat(7) { add(detailsCredit) } },
)
