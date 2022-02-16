package com.tutuland.wof.android

import android.os.Bundle
import androidx.activity.ComponentActivity
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
    }

    override fun onBackPressed() {
        if (navigator.goBack().not()) super.onBackPressed()
    }
}
