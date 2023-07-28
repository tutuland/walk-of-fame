package com.tutuland.wof.common.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.theme.OverlayColorEnd
import com.tutuland.wof.common.theme.OverlayColorStart
import com.tutuland.wof.common.utils.NetworkImage
import com.tutuland.wof.core.details.data.DetailsModel

@Composable
fun DetailsContent(model: DetailsModel, config: DetailsScreenConfig, fullBioClicked: () -> Unit) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = config.contentPadding)
    ) {
        if (config.isWide) addWideContent(model, config, fullBioClicked)
        else addCompactContent(model, config, fullBioClicked)

        addCredits(model, config)
    }
}

fun LazyListScope.addWideContent(
    model: DetailsModel,
    config: DetailsScreenConfig,
    fullBioClicked: () -> Unit,
    t: @Composable () -> Unit = {}, // https://github.com/JetBrains/compose-multiplatform/issues/3087
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DetailsHeaderImage(model, config, Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = config.contentPadding),
            ) {
                DetailsHeaderTexts(
                    model = model,
                    maxLines = config.headerMaxLines,
                    modifier = Modifier.padding(horizontal = config.contentPadding)
                )
                DetailsBio(model, config, fullBioClicked)
            }
        }
    }
}

fun LazyListScope.addCompactContent(
    model: DetailsModel,
    config: DetailsScreenConfig,
    fullBioClicked: () -> Unit,
    t: @Composable () -> Unit = {}, // https://github.com/JetBrains/compose-multiplatform/issues/3087
) {
    item { DetailsHeader(model, config) }
    if (model.biography.isNotBlank()) item { DetailsBio(model, config, fullBioClicked) }
}

@Composable
fun DetailsHeader(model: DetailsModel, config: DetailsScreenConfig, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
    ) {
        DetailsHeaderImage(model, config)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(OverlayColorEnd, OverlayColorStart)
                    )
                )
                .padding(horizontal = config.contentPadding)
                .padding(bottom = 4.dp, top = 64.dp)
        ) {
            DetailsHeaderTexts(model, config.headerMaxLines)
        }
    }
}

@Composable
fun DetailsHeaderImage(
    model: DetailsModel,
    config: DetailsScreenConfig,
    modifier: Modifier = Modifier
) {
    NetworkImage(
        url = model.pictureUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        crossfade = true,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = config.headerImageMinHeight)
    )
}

@Composable
fun DetailsHeaderTexts(model: DetailsModel, maxLines: Int, modifier: Modifier = Modifier) {
    Text(
        text = model.name,
        style = MaterialTheme.typography.h1,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
    if (model.department.isNotBlank()) Text(
        text = model.department,
        style = MaterialTheme.typography.subtitle1,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(top = 4.dp),
    )
    if (model.bornIn.isNotBlank()) Text(
        text = model.bornIn,
        style = MaterialTheme.typography.subtitle2,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(top = 8.dp),
    )
    if (model.diedIn.isNotBlank()) Text(
        text = model.diedIn,
        style = MaterialTheme.typography.subtitle2,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(top = 8.dp),
    )
}

@Composable
fun DetailsBio(
    model: DetailsModel,
    config: DetailsScreenConfig,
    fullBioClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = config.contentPadding)
            .padding(top = config.contentPadding)
    ) {
        Text(
            text = model.biography,
            style = MaterialTheme.typography.body1,
            maxLines = config.bioMaxLines,
            overflow = TextOverflow.Ellipsis,
        )
        if (config.bioMaxLines < Int.MAX_VALUE) Text(
            text = "See full bio",
            style = MaterialTheme.typography.body2.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .clickable { fullBioClicked() }
                .padding(vertical = 16.dp)
                .padding(end = 16.dp),
        )
    }
}
