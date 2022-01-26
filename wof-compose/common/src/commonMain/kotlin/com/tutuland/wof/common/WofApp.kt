package com.tutuland.wof.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.details.DetailsScreen
import com.tutuland.wof.common.search.SearchHeader
import com.tutuland.wof.common.search.SearchResult
import com.tutuland.wof.common.theme.WofTheme
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.CoroutineScope

@Composable
fun WofApp(coroutineScope: CoroutineScope) {
    //TODO: setup navigation routes
    WofTheme {
        TestDetails()
    }
}

@Composable
fun TestSearch() {
    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            val searchModel = Search.Model(
                id = "",
                name = "Chloé Zhao",
                department = "Director • Screenwriter • Producer",
                knownFor = listOf()
            )
            SearchHeader()

            Spacer(Modifier.height(48.dp))

            SearchResult(searchModel)

        }
    }
}

@Composable
fun TestDetails() {
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

    DetailsScreen(detailsModel)
}
