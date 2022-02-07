package com.tutuland.wof.common.utils

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.tutuland.wof.common.theme.ButtonBackgroundColor
import com.tutuland.wof.common.theme.ButtonIconColor
import com.tutuland.wof.common.theme.CircleCornerShape

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(color = ButtonBackgroundColor, shape = CircleCornerShape)
            .semantics { contentDescription = "Go back" },
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = ButtonIconColor,
            contentDescription = null,
        )
    }
}
