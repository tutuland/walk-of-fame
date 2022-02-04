package details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import components.Container
import components.SingleLineText
import components.TextSubHeader
import components.WofButton
import navigation.Navigator
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Tr

@Composable
fun DetailsScreen(id: String, viewModel: DetailsViewModel, nav: Navigator) {
    val state: DetailsViewModel.ViewState by viewModel.viewState.collectAsState()
    Container {
        WofButton("Back", 64) { nav.goBack() }
        if (state.isLoading) Text("Loading..")
        if (state.showError) Text("Error!")
        state.details?.let { DetailsContent(it, nav) }
    }
}

@Composable
fun DetailsContent(details: Details.Model, nav: Navigator) {
    Br()
    Img(
        src = details.pictureUrl,
        attrs = {
            style { width(300.px) }
        }
    )
    FullBioHeader(details)
    WofButton("See full bio", 96) { nav.goToFullBio() }
    DetailsCredits(details)
}

@Composable
fun DetailsCredits(details: Details.Model) {
    TextSubHeader("Known for")
    Table(
        attrs = {
            style { width(330.px) }
        }
    ) {
        val rows = details.credits.chunked(3)
        rows.forEach { DetailsCreditsRow(it) }
    }
}

@Composable
fun DetailsCreditsRow(creditsRow: List<Details.Model.Credit>) {
    Tr {
        creditsRow.forEach { DetailsCredit(it) }
    }
}

@Composable
fun DetailsCredit(credit: Details.Model.Credit) {
    Td {
        Img(
            src = credit.posterUrl.ifBlank { "placeholder.svg" },
            attrs = {
                style { width(100.px) }
            }
        )
        SingleLineText(credit.title, 100)
        SingleLineText(credit.credit, 100)
        SingleLineText(credit.year, 100)
    }
}
