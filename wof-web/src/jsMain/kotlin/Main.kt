import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.tutuland.wof.core.injectOnJs
import navigation.WebNavigator
import org.jetbrains.compose.web.renderComposable
import theme.WofTheme

fun main() {
    renderComposable(rootElementId = "root") {
        val scope = rememberCoroutineScope()
        injectOnJs(scope)
        val navigator = remember { WebNavigator() }
        val currentScreen by navigator.currentScreen.collectAsState()
        WofTheme {
            currentScreen()
        }
    }
}
