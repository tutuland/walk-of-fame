import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import navigation.WebNavigator
import org.jetbrains.compose.web.renderComposable
import theme.WofTheme

fun main() {
    renderComposable(rootElementId = "root") {
        val navigator = remember { WebNavigator() }
        val currentScreen by navigator.currentScreen.collectAsState()
        WofTheme {
            currentScreen()
        }
    }
}
