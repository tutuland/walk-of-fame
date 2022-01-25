package com.tutuland.wof.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.search.SearchHeader
import com.tutuland.wof.common.search.SearchResult
import com.tutuland.wof.common.theme.WofTheme
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

@Composable
fun WofApp(viewModel: SearchViewModel) {
    //TODO: setup navigation routes
    WofTheme {
        //SearchScreen(viewModel)
        TestTypography()
    }
}

@Composable
fun TestTypography() {
    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            SearchHeader()

            Spacer(Modifier.height(48.dp))

            SearchResult(
                Search.Model("", "Chloé Zhao", "Director • Screenwriter • Producer", listOf())
            )

            Spacer(Modifier.height(48.dp))

            Text(
                text = "H1: Chloé Zhao",
                style = MaterialTheme.typography.h1,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "S1: Director • Screenwriter • Producer",
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "S2: Born in March 31, 1982 in Beijing, China",
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "B1: Chloé Zhao or Zhao Ting (born March 31, 1982) is a Chinese film director, screenwriter, and producer. Her debut feature film, Songs My Brothers...",
                style = MaterialTheme.typography.body1,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "B2: See full bio",
                style = MaterialTheme.typography.body2.copy(textDecoration = TextDecoration.Underline),
            )
            Spacer(Modifier.height(48.dp))

            Text(
                text = "H3: Known For",
                style = MaterialTheme.typography.h2,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "H5: Eternals",
                style = MaterialTheme.typography.h5,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "S2: Director",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "S2: 2021",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
            )
        }
    }
}
