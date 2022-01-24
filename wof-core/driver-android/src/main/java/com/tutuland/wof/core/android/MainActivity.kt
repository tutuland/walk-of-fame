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
import com.tutuland.wof.core.ServiceLocator.log
import com.tutuland.wof.core.ServiceLocator.requestDetails
import com.tutuland.wof.core.ServiceLocator.searchForPeople
import com.tutuland.wof.core.android.ui.theme.WoFCoreTheme
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
            val results = mutableListOf<Search.Model>()
            searchForPeople.withName("Chadwick Boseman")
                .catch { log.d("Failure on searchApi!\n--------\n$it") }
                .onEach {
                    log.d("Received: $it")
                    results.add(it)
                }
                .onCompletion {
                    if (it == null) {
                        val model = results[0]
                        runCatching {
                            requestDetails.with(model.id, model.knownFor)
                        }.onSuccess { result ->
                            log.d("Success on detailsApi: $result")
                        }.onFailure {
                            log.d("Failure on detailsApi!\n--------\n$it")
                        }
                    } else log.d("SearchApi ended with FAILURE!")
                }.collect()
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
