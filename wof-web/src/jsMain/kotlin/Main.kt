import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.tutuland.wof.core.ServiceLocator
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        val scope = rememberCoroutineScope()
        val text = remember { mutableStateOf("Loading...") }
        Div({ style { padding(25.px) } }) {
            Span({ style { padding(15.px) } }) {
                Text(text.value)
            }
        }

        scope.launch {
            val results = mutableListOf<Search.Model>()
            ServiceLocator.searchForPeople.withName("Chadwick Boseman")
                .catch { ServiceLocator.log.d("Failure on searchApi!\n--------\n$it") }
                .onEach {
                    ServiceLocator.log.d("Received: $it")
                    results.add(it)
                }
                .onCompletion {
                    if (it == null) {
                        val model = results[0]
                        runCatching {
                            ServiceLocator.requestDetails.with(model.id)
                        }.onSuccess { result ->
                            ServiceLocator.log.d("Success on detailsApi: $result")
                            text.value = "Success:\n$result"
                        }.onFailure {
                            ServiceLocator.log.d("Failure on detailsApi!\n--------\n$it")
                            text.value = "Failure!"
                        }
                    } else {
                        ServiceLocator.log.d("SearchApi ended with FAILURE!")
                        text.value = "Failure!"
                    }
                }.collect()
        }
    }
}
