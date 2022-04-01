package details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.details.ui.DetailsViewModel
import components.Container
import components.TextBody
import components.TextHeader
import components.WofButton
import navigation.Navigator

@Composable
fun FullBioScreen(viewModel: DetailsViewModel, nav: Navigator) {
    val state: DetailsViewModel.ViewState by viewModel.viewState.collectAsState()
    Container {
        WofButton("Back", 64) { nav.goBack() }
        state.details?.let { details ->
            FullBioHeader(details)
            TextBody(details.biography)
        }
    }
}

@Composable
fun FullBioHeader(details: DetailsModel) {
    TextHeader(details.name)
    TextBody(details.department)
    TextBody(details.bornIn)
    TextBody(details.diedIn)
}
