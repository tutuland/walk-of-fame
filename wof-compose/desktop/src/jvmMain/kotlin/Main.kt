import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tutuland.wof.common.WofApp
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

fun main() = application {
    val scope = rememberCoroutineScope()
    val viewModel = SearchViewModel(scope)
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            WofApp(viewModel)
        }
    }
}
