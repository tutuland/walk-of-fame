package com.tutuland.wof.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tutuland.wof.common.WofApp
import com.tutuland.wof.common.navigation.AndroidNavigator

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var navigator: AndroidNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberAnimatedNavController()
            navigator = AndroidNavigator(navController)
            WofApp(navigator)
        }
        onBackPressedDispatcher.addCallback(this) { backPressed() }
    }

    private fun backPressed() {
        if (navigator.goBack().not()) finish()
    }
}
