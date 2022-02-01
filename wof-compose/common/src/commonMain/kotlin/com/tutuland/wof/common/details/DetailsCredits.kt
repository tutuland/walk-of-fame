package com.tutuland.wof.common.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.utils.NetworkImage
import com.tutuland.wof.core.details.Details

fun LazyListScope.addCredits(model: Details.Model, config: DetailsScreenConfig) {
    item { DetailsCreditsHeader(config.contentPadding) }

    val rows = model.credits.chunked(config.creditColumns)
    items(rows.size) { index ->
        DetailsCreditsRow(rows[index], config)
    }
}

@Composable
fun DetailsCreditsHeader(contentPadding: Dp) {
    Text(
        text = "Known For",
        style = MaterialTheme.typography.h2,
        modifier = Modifier
            .padding(horizontal = contentPadding)
            .padding(top = contentPadding)
    )
}

@Composable
fun DetailsCreditsRow(credits: List<Details.Model.Credit>, config: DetailsScreenConfig) {
    if (credits.isEmpty() || credits.size > config.creditColumns)
        throw IllegalArgumentException("Credits on a row cannot be zero or exceed number of columns")

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = config.contentPadding)
            .padding(top = config.contentPadding)
            .semantics(mergeDescendants = true) {}
    ) {
        repeat(config.creditColumns) { index ->
            val modifier = Modifier.weight(1f)
            if (index < credits.size) DetailsCredit(credits[index], config, modifier)
            else Spacer(modifier)
            if (index < (config.creditColumns - 1)) Spacer(Modifier.width(config.contentPadding))
        }
    }
}

@Composable
fun DetailsCredit(credit: Details.Model.Credit, config: DetailsScreenConfig, modifier: Modifier) {
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
                .defaultMinSize(minHeight = config.creditsImageMinHeight)
        )
        Text(
            text = credit.title,
            style = MaterialTheme.typography.h5,
            maxLines = config.creditMaxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = credit.credit,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondary,
            maxLines = config.creditMaxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = credit.year,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondary,
            maxLines = config.creditMaxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
