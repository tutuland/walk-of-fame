package com.tutuland.wof.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tutuland.wof.common.WofApp
import com.tutuland.wof.common.WofNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : ComponentActivity() {
    private lateinit var scope: CoroutineScope
    private lateinit var navigator: WofNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        scope = MainScope()
        navigator = WofNavigator(scope, exitApp = ::finish)
        setContent {
            WofApp(navigator)
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onBackPressed() {
        navigator.goBack()
    }
}
