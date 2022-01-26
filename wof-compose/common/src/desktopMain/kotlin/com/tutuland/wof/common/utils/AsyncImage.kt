package com.tutuland.wof.common.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import com.tutuland.wof.core.ServiceLocator.log
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Based on https://github.com/JetBrains/compose-jb/tree/master/tutorials/Image_And_Icons_Manipulations
 */
@Composable
actual fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale,
    crossfade: Boolean,
) {

    val painterFor: @Composable (ImageBitmap) -> Painter = { remember { BitmapPainter(it) } }
    val image: ImageBitmap? by produceState<ImageBitmap?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                if (url.isBlank()) throw IllegalArgumentException("Image url cannot be empty")
                loadImageBitmap(url)
            } catch (e: Exception) {
                log.e("AsyncImage error: $e")
                null
            }
        }
    }

    Image(
        painter = image?.let { painterFor(it) } ?: painterResource("MR/images/placeholder@4x.png"),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
