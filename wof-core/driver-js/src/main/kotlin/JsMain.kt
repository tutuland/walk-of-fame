import com.tutuland.wof.core.search.JsSearchApi
import kotlinx.browser.document

fun main() {
        JsSearchApi().searchFor("Wes Anderson") { result ->
                val div = document.createElement("pre")
                if (result == "") {
                        div.textContent = "Failure!"
                } else {
                        div.textContent = "Success!"
                }
                document.body?.appendChild(div)
        }
}
