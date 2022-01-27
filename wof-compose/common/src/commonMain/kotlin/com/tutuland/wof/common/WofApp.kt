package com.tutuland.wof.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.tutuland.wof.common.theme.WofTheme

@Composable
fun WofApp(navigator: WofNavigator) {
    val currentNavigator = remember { navigator }
    val currentScreen by currentNavigator.currentScreen.collectAsState()
    WofTheme {
        currentScreen()
    }
}
