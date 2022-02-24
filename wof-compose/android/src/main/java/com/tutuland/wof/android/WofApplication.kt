package com.tutuland.wof.android

import android.app.Application
import com.tutuland.wof.core.injectOnAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class WofApplication : Application() {
    private lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        scope = MainScope()
        injectOnAndroid(scope, applicationContext)
    }

    override fun onTerminate() {
        scope.cancel()
        super.onTerminate()
    }
}
