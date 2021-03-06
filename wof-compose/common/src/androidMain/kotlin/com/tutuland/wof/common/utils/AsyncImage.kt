package com.tutuland.wof.common.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.tutuland.wof.common.R

@Composable
actual fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale,
    crossfade: Boolean,
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(crossfade)
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
        ),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}
