package com.tutuland.wof.common.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.navigation.Navigator
import com.tutuland.wof.common.theme.BrandAccentColor
import com.tutuland.wof.core.search.repository.SearchModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

// TODO: make these constants adaptive
private val contentPadding = 16.dp

@Composable
fun SearchScreen(viewModel: SearchViewModel, nav: Navigator) {
    Scaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchHeader(viewModel, contentPadding)
            SearchContent(viewModel, contentPadding, onResultClicked = { nav.goToDetailsFor(it.id) })
        }
    }
}


@Composable
fun SearchContent(viewModel: SearchViewModel, contentPadding: Dp, onResultClicked: (SearchModel) -> Unit) {
    val state: SearchViewModel.ViewState by viewModel.viewState.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.searchResults.isNotEmpty()) SearchResults(state.searchResults, contentPadding, onResultClicked)
        if (state.isLoading) SearchLoading()
        if (state.showError) SearchErrorState(state.searchedTerm)
    }
}

@Composable
fun SearchResults(searchResults: List<SearchModel>, contentPadding: Dp, onResultClicked: (SearchModel) -> Unit) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxSize(),
    ) {
        item { Spacer(Modifier.height(16.dp)) }
        items(count = searchResults.size) { index ->
            SearchResult(searchResults[index], contentPadding, onResultClicked)
        }
        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
fun SearchResult(model: SearchModel, contentPadding: Dp, onResultClicked: (SearchModel) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClickLabel = "See details") { onResultClicked(model) }
            .padding(vertical = 16.dp, horizontal = contentPadding)
            .semantics(mergeDescendants = true) {}

    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = model.name,
                style = MaterialTheme.typography.h3,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = model.department,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.secondary,
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
            modifier = Modifier
                .scale(0.7f)
                .alpha(0.7f)
        )
    }
}

@Composable
fun SearchLoading() {
    CircularProgressIndicator(color = BrandAccentColor)
}

@Composable
fun SearchErrorState(searchTerm: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp)
            .semantics(mergeDescendants = true) {},
    ) {
        Text(
            text = "Couldn't find\n\"$searchTerm\"",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Please, try again using\ndifferent terms or spelling",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}
