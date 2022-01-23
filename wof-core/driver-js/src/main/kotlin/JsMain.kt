import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator.requestDetails
import com.tutuland.wof.core.ServiceLocator.searchForPeople
import com.tutuland.wof.core.search.Search
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun main() {
    val div = document.createElement("pre")
    GlobalScope.launch {
        val results = mutableListOf<Search.Model>()
        searchForPeople.withName("Chadwick Boseman")
            .catch { Logger.d("Failure on searchApi!\n--------\n$it") }
            .onEach {
                Logger.d("Received: $it")
                results.add(it)
            }
            .onCompletion {
                if (it == null) {
                    val model = results[0]
                    runCatching {
                        requestDetails.with(model.id, model.knownFor)
                    }.onSuccess { result ->
                        Logger.d("Success on detailsApi: $result")
                        div.textContent = "Success!"
                    }.onFailure {
                        Logger.d("Failure on detailsApi!\n--------\n$it")
                        div.textContent = "Failure!"
                    }
                } else {
                    Logger.d("SearchApi ended with FAILURE!")
                    div.textContent = "Failure!"
                }
            }.collect()
    }
    document.body?.appendChild(div)
}
