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
import com.tutuland.wof.core.ServiceLocator.detailsApi
import com.tutuland.wof.core.ServiceLocator.searchApi
import com.tutuland.wof.core.android.ui.theme.WoFCoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var scope: CoroutineScope

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
                searchApi.searchFor("Wes Anderson")
            }.onSuccess { result ->
                Logger.d("Success on searchApi: $result")
                result.people.orEmpty().getOrNull(0)?.id?.let { id ->
                    runCatching {
                        detailsApi.getDetailsFor(id.toString())
                    }.onSuccess { result ->
                        Logger.d("Success on detailsApi: $result")
                    }.onFailure {
                        Logger.d("Failure on detailsApi!\n--------\n$it")
                    }
                }
            }.onFailure {
                Logger.d("Failure on searchApi!\n--------\n$it")
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
