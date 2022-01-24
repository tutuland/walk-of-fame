package com.tutuland.wof.common

import androidx.compose.runtime.Composable
import com.tutuland.wof.common.search.SearchScreen
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

@Composable
fun WofApp(viewModel: SearchViewModel) {
    //TODO: setup navigation routes
    SearchScreen(viewModel)
}
