package com.tutuland.wof.common.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.tutuland.wof.common.R


actual val workSans: FontFamily = FontFamily(
    Font(R.font.worksans_regular),
    Font(R.font.worksans_medium, FontWeight.Medium),
)

actual val volkhov: FontFamily = FontFamily(
    Font(R.font.volkhov_regular),
    Font(R.font.volkhov_bold, FontWeight.Bold),
)
