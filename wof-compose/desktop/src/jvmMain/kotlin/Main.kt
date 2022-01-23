import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tutuland.wof.common.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App()
        }
    }
}
