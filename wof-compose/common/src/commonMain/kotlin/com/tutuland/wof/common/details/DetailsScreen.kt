package com.tutuland.wof.common.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.navigation.Navigator
import com.tutuland.wof.common.theme.BrandAccentColor
import com.tutuland.wof.common.utils.BackButton
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel

// TODO: make these constants adaptive
private val contentPadding = 16.dp
private const val creditColumns = 2

@Composable
fun DetailsScreen(id: String, viewModel: DetailsViewModel, nav: Navigator) {
    val viewState: DetailsViewModel.ViewState by viewModel.viewState.collectAsState()
    Scaffold {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize(),
        ) {
            viewState.details?.let { DetailsContent(it, contentPadding, creditColumns, nav::goToFullBio) }
            if (viewState.isLoading) DetailsLoading()
            if (viewState.showError) DetailsErrorState(onRetry = { viewModel.requestDetailsWith(id) })
            BackButton(Modifier.padding(contentPadding), onClick = nav::goBack)
        }
    }
}

@Composable
fun DetailsLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = BrandAccentColor)
    }
}

@Composable
fun DetailsErrorState(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Something went wrong!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "Try Again")
        }
    }
}
