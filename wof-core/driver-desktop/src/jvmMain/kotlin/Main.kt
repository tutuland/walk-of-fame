import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tutuland.wof.core.ServiceLocator.log
import com.tutuland.wof.core.ServiceLocator.requestDetails
import com.tutuland.wof.core.ServiceLocator.searchForPeople
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
    val scope = rememberCoroutineScope()

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
                        requestDetails.with(model.id)
                    }.onSuccess { result ->
                        log.d("Success on detailsApi: $result")
                    }.onFailure {
                        log.d("Failure on detailsApi!\n--------\n$it")
                    }
                } else log.d("SearchApi ended with FAILURE!")
            }.collect()
    }
}

@Composable
fun App() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello, Desktop!")
    }
}