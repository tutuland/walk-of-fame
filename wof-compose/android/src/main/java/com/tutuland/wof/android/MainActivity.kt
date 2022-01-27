package com.tutuland.wof.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WofAppController.bindAsNavigationRoot(this)
    }

    override fun onDestroy() {
        WofAppController.unbindAsNavigationRoot()
        super.onDestroy()
    }

    override fun onBackPressed() {
        WofAppController.onBackPressed()
    }
}
