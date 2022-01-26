package com.tutuland.wof.common.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.theme.OverlayColorEnd
import com.tutuland.wof.common.theme.OverlayColorStart
import com.tutuland.wof.common.utils.BackButton
import com.tutuland.wof.common.utils.NetworkImage
import com.tutuland.wof.core.ServiceLocator.log
import com.tutuland.wof.core.details.Details

// TODO: make these constants adaptive
private val contentPadding = 16.dp
private const val creditColumns = 2

//TODO: receive viewModel
@Composable
fun DetailsScreen(model: Details.Model) {
    Scaffold {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize(),
        ) {
            DetailsContent(model, contentPadding, creditColumns)
            BackButton(Modifier.padding(contentPadding)) { log.d("BACK CLICKED!") }
        }
    }
}

@Composable
fun DetailsContent(model: Details.Model, contentPadding: Dp, creditColumns: Int) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailsHeader(model, contentPadding)
        DetailsBio(model, contentPadding)
        DetailsCredits(model.credits, creditColumns, contentPadding)
    }
}

@Composable
fun DetailsHeader(model: Details.Model, contentPadding: Dp) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
    ) {
        NetworkImage(
            url = model.pictureUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            crossfade = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            OverlayColorEnd,
                            OverlayColorStart,
                        )
                    )
                )
                .padding(horizontal = contentPadding)
                .padding(bottom = 4.dp, top = 64.dp)
        ) {
            Text(
                text = model.name,
                style = MaterialTheme.typography.h1,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = model.department,
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = model.bornIn,
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = model.diedIn,
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun DetailsBio(model: Details.Model, contentPadding: Dp) {
    Column(
        modifier = Modifier
            .padding(horizontal = contentPadding)
            .padding(top = 16.dp)
    ) {
        Text(
            text = model.biography,
            style = MaterialTheme.typography.body1,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "See full bio",
            style = MaterialTheme.typography.body2.copy(textDecoration = TextDecoration.Underline),
        )
    }
}

@Composable
fun DetailsCredits(credits: List<Details.Model.Credit>, columns: Int, contentPadding: Dp) {
    Column(modifier = Modifier.padding(contentPadding)) {
        Text(
            text = "Known For",
            style = MaterialTheme.typography.h2,
        )

        val rows = credits.chunked(columns)
        rows.forEach { DetailsCreditsRow(it, columns) }
    }
}

@Composable
fun DetailsCreditsRow(credits: List<Details.Model.Credit>, columns: Int) {
    if (credits.isEmpty() || credits.size > columns)
        throw IllegalArgumentException("Credits on a row cannot be zero or exceed number of columns")

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics(mergeDescendants = true) {}
    ) {
        repeat(columns) { index ->
            val modifier = Modifier.weight(1f)
            if (index < credits.size) DetailsCredit(credits[index], modifier)
            else Spacer(modifier)
            if (index < (columns - 1)) Spacer(Modifier.width(16.dp))
        }
    }
}

@Composable
fun DetailsCredit(credit: Details.Model.Credit, modifier: Modifier) {
    Column(
        modifier = modifier.semantics(mergeDescendants = true) {}
    ) {
        NetworkImage(
            url = credit.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            crossfade = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
        )
        Text(
            text = credit.title,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = credit.credit,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = credit.year,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondary,
        )
    }
}
