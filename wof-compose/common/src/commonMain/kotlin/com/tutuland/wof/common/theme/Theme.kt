package com.tutuland.wof.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val BrandPrimary = Color(0xFF12121d)
val BrandPrimaryVariant = Color(0xFFE5E5E5)
val BrandSecondary = Color(0xFFA0A0A0)
val BrandOnPrimary = Color(0xFFFFFFFF)

val ColorPalette = darkColors(
    primary = BrandPrimary,
    primaryVariant = BrandPrimaryVariant,
    secondary = BrandSecondary,
    background = BrandPrimary,
    surface = BrandPrimary,
    onPrimary = BrandOnPrimary,
    onSecondary = BrandOnPrimary,
    onBackground = BrandOnPrimary,
    onSurface = BrandOnPrimary,
)

expect val workSans: FontFamily
expect val volkhov: FontFamily

val BrandTypography: Typography = Typography(
    h1 = TextStyle(
        fontFamily = volkhov,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp,
    ),
    h2 = TextStyle(
        fontFamily = volkhov,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
    ),
    h3 = TextStyle(
        fontFamily = volkhov,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    h4 = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
    ),
    h5 = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 28.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = volkhov,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 28.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 28.sp,
    ),
    body1 = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    body2 = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 24.sp,
    )
)

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun WofTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = BrandTypography,
        shapes = Shapes,
        content = content
    )
}
