import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tutuland.wof.common.WofApp

fun main() = application {
    val scope = rememberCoroutineScope()
    val title = "Walk of Fame"
    val icon = painterResource("ic_launcher.png")

    Tray(
        icon = icon,
        menu = {
            Item(
                text = "Quit $title",
                onClick = ::exitApplication,
            )
        }
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = title,
        icon = icon,
    ) {
        WofApp(scope)
    }
}

@Preview
@Composable
fun WofAppPreview() {
    val scope = rememberCoroutineScope()
    WofApp(scope)
}
