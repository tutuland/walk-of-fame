package components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import theme.WofStylesheet

@Composable
fun Container(content: @Composable () -> Unit) {
    Div(
        attrs = { classes(WofStylesheet.container) }
    ) {
        content()
    }
}

@Composable
fun WofButton(text: String, width: Int = 0, onClick: () -> Unit) {
    Button(
        attrs = {
            if (width > 0) style { width(width.px) }
            onClick { onClick() }
        }
    ) { Text(text) }
}

@Composable
fun TextHeader(text: String) {
    H1 { Text(text) }
}

@Composable
fun TextSubHeader(text: String) {
    H3 { Text(text) }
}


@Composable
fun TextBody(text: String) {
    if (text.isNotBlank()) {
        Text(text)
        Br()
        Br()
    }
}

@Composable
fun SingleLineText(text: String, width: Int = 0) {
    Br()
    Span(
        attrs = {
            if (width > 0) style { width(width.px) }
            classes(WofStylesheet.singleLine)
        }
    ) {
        Text(text)
    }
}
