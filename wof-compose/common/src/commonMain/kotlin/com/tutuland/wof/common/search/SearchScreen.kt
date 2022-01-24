package com.tutuland.wof.common.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Walk of Fame",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )
        SearchContent(viewModel)
    }
    viewModel.searchFor("Wes Anderson")
}

@Composable
fun SearchContent(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val viewState: SearchViewModel.ViewState by viewModel.viewState.collectAsState()
    val onRetry = { viewModel.searchFor("Wes Anderson") } //TODO
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        SearchResults(viewState.searchResults)
        if (viewState.isLoading) SearchLoading()
        if (viewState.showError) SearchErrorState(onRetry = onRetry)
    }
}

@Composable
fun SearchResults(searchResults: List<Search.Model>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(count = searchResults.size) { index ->
            SearchResult(searchResults[index])
        }
    }
}

@Composable
fun SearchResult(model: Search.Model) {
    Text(
        text = model.name,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SearchLoading() {
    CircularProgressIndicator()
}

@Composable
fun SearchErrorState(onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Something went wrong!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "Try Again")
        }
    }
}
