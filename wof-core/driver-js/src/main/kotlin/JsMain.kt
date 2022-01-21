import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    val div = document.createElement("pre")
    val api = ServiceLocator.searchApi
    GlobalScope.launch {
        runCatching {
            api.searchFor("Wes Anderson")
        }.onSuccess {
            Logger.d("Success!")
            div.textContent = "Success!"
        }.onFailure {
            Logger.d("Failure!\n--------\n$it")
            div.textContent = "Failure!"
        }
    }
    document.body?.appendChild(div)
}
