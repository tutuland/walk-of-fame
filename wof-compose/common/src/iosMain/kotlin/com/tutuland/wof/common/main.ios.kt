package com.tutuland.wof.common

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.seiko.imageloader.LocalImageLoader
import com.tutuland.wof.common.navigation.NativeNavigator
import com.tutuland.wof.common.utils.generateImageLoader
import com.tutuland.wof.core.injectOnIOS
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    injectOnIOS()
    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalImageLoader provides generateImageLoader(),
        ) {
            val navigator = NativeNavigator(finishNavigation = {}) //TODO
            WofApp(navigator)
        }
    }
}