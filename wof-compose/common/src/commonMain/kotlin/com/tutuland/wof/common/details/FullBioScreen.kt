package com.tutuland.wof.common.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.navigation.Navigator
import com.tutuland.wof.common.utils.BackButton
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel

// TODO: make these constants adaptive
private val contentPadding = 16.dp

@Composable
fun FullBioScreen(viewModel: DetailsViewModel, nav: Navigator) {
    Scaffold {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize(),
        ) {
            viewModel.viewState.value.details?.let { FullBioContent(it, contentPadding) }
            BackButton(modifier = Modifier.padding(contentPadding), onClick = nav::goBack)
        }
    }
}

@Composable
fun FullBioContent(model: Details.Model, contentPadding: Dp) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = contentPadding)
            .verticalScroll(scrollState)
            .semantics(mergeDescendants = true) {},
    ) {
        Spacer(Modifier.height(96.dp))
        DetailsHeaderTexts(model, maxLines = 2)
        Text(
            text = model.biography + model.biography + model.biography,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}
