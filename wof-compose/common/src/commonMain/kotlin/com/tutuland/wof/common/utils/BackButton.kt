package com.tutuland.wof.common.utils

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.theme.ButtonBackgroundColor
import com.tutuland.wof.common.theme.ButtonIconColor

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = ButtonBackgroundColor,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = ButtonIconColor,
            contentDescription = null,
        )
    }
}
