package search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import components.Container
import components.TextBody
import components.TextHeader
import components.WofButton
import navigation.Navigator
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Tr

@Composable
fun SearchScreen(viewModel: SearchViewModel, nav: Navigator) {
    val state: SearchViewModel.ViewState by viewModel.viewState.collectAsState()
    val searchText = remember { mutableStateOf("") }
    Container {
        TextHeader("Walk of fame")
        TextBody("Let's find movie stars and amazing creativity people!")

        Table(
            attrs = {
                style { width(200.px) }
            }
        ) {
            Tr {
                Td {
                    Input(type = InputType.Text) {
                        value(searchText.value)
                        onInput { event -> searchText.value = event.value }
                    }
                }
                Td {
                    WofButton("Search") { viewModel.searchFor(searchText.value) }
                }
            }
        }

        Br { }

        state.searchResults.forEach { model ->
            WofButton(model.name, 330) { nav.goToDetailsFor(model.id) }
            Br { }
        }
    }
}
