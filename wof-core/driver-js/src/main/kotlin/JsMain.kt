import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    val div = document.createElement("pre")
    val searchApi = ServiceLocator.searchApi
    val detailsApi = ServiceLocator.detailsApi
    GlobalScope.launch {
        runCatching {
            searchApi.searchFor("Wes Anderson")
        }.onSuccess { result ->
            Logger.d("Success on searchApi: $result")
            result.people.orEmpty().getOrNull(0)?.id?.let { id ->
                runCatching {
                    detailsApi.getDetailsFor(id.toString())
                }.onSuccess { result ->
                    Logger.d("Success on detailsApi: $result")
                    div.textContent = "Success!"
                }.onFailure {
                    Logger.d("Failure on detailsApi!\n--------\n$it")
                    div.textContent = "Failure!"
                }
            }
        }.onFailure {
            Logger.d("Failure on searchApi!\n--------\n$it")
            div.textContent = "Failure!"
        }
    }
    document.body?.appendChild(div)
}
