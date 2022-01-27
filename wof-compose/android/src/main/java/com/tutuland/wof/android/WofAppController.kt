package com.tutuland.wof.android

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tutuland.wof.common.WofApp
import com.tutuland.wof.common.WofNavigator
import com.tutuland.wof.core.ServiceLocator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class WofAppController : Application() {
    companion object {
        private lateinit var navigator: WofNavigator
        private lateinit var scope: CoroutineScope
        private val noActivityRegistered = { ServiceLocator.log.e("No activity registered to finish navigation.") }

        fun bindAsNavigationRoot(activity: ComponentActivity) {
            navigator.finishNavigation = activity::finish
            activity.setContent {
                WofApp(navigator)
            }
        }

        fun unbindAsNavigationRoot() {
            navigator.finishNavigation = noActivityRegistered
        }

        fun onBackPressed() {
            navigator.goBack()
        }
    }

    override fun onCreate() {
        super.onCreate()
        scope = MainScope()
        navigator = WofNavigator(scope, finishNavigation = noActivityRegistered)
    }

    override fun onTerminate() {
        scope.cancel()
        super.onTerminate()
    }
}
