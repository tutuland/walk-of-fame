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
import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import com.tutuland.wof.core.android.ui.theme.WoFCoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var scope: CoroutineScope
    private val api = ServiceLocator.searchApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = MainScope()
        setContent {
            WoFCoreTheme {
                App()
            }
        }

        scope.launch {
            runCatching {
                api.searchFor("Wes Anderson")
            }.onSuccess {
                Logger.d("Success!")
            }.onFailure {
                Logger.d("Failure!\n--------\n$it")
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}

@Composable
fun App() {
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Hello, Android!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
