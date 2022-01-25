package com.tutuland.wof.common.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

actual val workSans: FontFamily = FontFamily(
    Font(
        resource = "fonts/WorkSans-Regular.ttf",
        weight = FontWeight.W400,
        style = FontStyle.Normal,
    ),
    Font(
        resource = "fonts/WorkSans-Medium.ttf",
        weight = FontWeight.W500,
        style = FontStyle.Normal,
    ),
)

actual val volkhov: FontFamily = FontFamily(
    Font(
        resource = "fonts/Volkhov-Regular.ttf",
        weight = FontWeight.W400,
        style = FontStyle.Normal,
    ),
    Font(
        resource = "fonts/Volkhov-Bold.ttf",
        weight = FontWeight.W700,
        style = FontStyle.Normal,
    ),
)
