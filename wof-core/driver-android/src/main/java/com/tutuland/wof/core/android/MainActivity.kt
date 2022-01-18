package com.tutuland.wof.core.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tutuland.wof.core.Greeting
import com.tutuland.wof.core.android.ui.theme.WoFCoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoFCoreTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = Greeting().greeting(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
