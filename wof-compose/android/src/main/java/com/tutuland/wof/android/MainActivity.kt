package com.tutuland.wof.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tutuland.wof.common.WofApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : ComponentActivity() {
    private lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        scope = MainScope()
        setContent {
            WofApp(scope)
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}

@Preview
@Composable
fun WofAppPreview() {
    val scope = rememberCoroutineScope()
    WofApp(scope)
}
