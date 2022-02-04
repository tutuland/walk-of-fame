package theme

import androidx.compose.runtime.Composable
import components.Container
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.overflow
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.whiteSpace

@Composable
fun WofTheme(content: @Composable () -> Unit) {
    Style(WofStylesheet)
    Container { content() }
}

object WofStylesheet : StyleSheet() {
    val container by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        height(100.percent)
        padding(16.px)
        boxSizing("border-box")
        fontSize(16.px)
        fontWeight(400)
    }

    val singleLine by style {
        display(DisplayStyle.InlineBlock)
        overflow("hidden")
        whiteSpace("nowrap")
        fontSize(13.px)
        property("clear", "both")
    }
}
