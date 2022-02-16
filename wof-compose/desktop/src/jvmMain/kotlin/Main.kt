import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.tutuland.wof.common.WofApp
import com.tutuland.wof.common.navigation.DesktopNavigator
import com.tutuland.wof.core.injectOnDesktop

fun main() = application {
    val scope = rememberCoroutineScope()
    injectOnDesktop(scope)
    val navigator = DesktopNavigator(finishNavigation = ::exitApplication)
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
        WofApp(navigator)
    }
}
