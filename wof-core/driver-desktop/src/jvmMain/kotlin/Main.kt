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
import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import kotlinx.coroutines.launch

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
    val scope = rememberCoroutineScope()
    val searchApi = ServiceLocator.searchApi
    val detailsApi = ServiceLocator.detailsApi

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