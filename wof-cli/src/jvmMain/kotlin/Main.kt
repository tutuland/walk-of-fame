import com.github.kinquirer.KInquirer
import com.github.kinquirer.components.promptInput
import com.github.kinquirer.components.promptListObject
import com.github.kinquirer.core.Choice
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.injectOnDesktop
import com.tutuland.wof.core.search.Search
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking

val HEADER = """
*******************************************************
*                   Walk of fame                      *
*******************************************************
*                                                     *
* Let's find movie stars and amazing creative people! *
*                                                     *
******************************************************* 
"""

fun main() {
    val scope = MainScope()
    val dependencies = injectOnDesktop(scope, shouldDisableLogger = true).koin
    val searchForPeople: Search.ForPeople = dependencies.get()
    val requestDetails: Details.Request = dependencies.get()
    runBlocking {
        println(HEADER)
        val id = searchForId(searchForPeople)
        detailsFor(id, requestDetails)
    }
    scope.cancel()
}

private suspend fun searchForId(searchForPeople: Search.ForPeople): String {
    val name = KInquirer.promptInput(message = "Who are you looking for?")
    val result = searchForPeople.withName(name).toCollection(mutableListOf())
    val choices = result.map { Choice(it.name, it) }
    val person = KInquirer.promptListObject(
        message = "You mean:",
        choices = choices,
        hint = "press Enter to pick",
        pageSize = 5,
    )
    return person.id
}

private suspend fun detailsFor(id: String, requestDetails: Details.Request) {
    val model = requestDetails.with(id)
    val option = KInquirer.promptListObject(
        message = "Are you interested in:",
        choices = listOf(
            Choice("Biography") { printFullBio(model) },
            Choice("Works") { printCredits(model) },
        ),
        hint = "press Enter to pick",
    )
    option()
    println("\n ~ The End ~")
}

private fun printFullBio(model: Details.Model) {
    println()
    println(model.name)
    if (model.department.isNotBlank()) println(model.department)
    if (model.bornIn.isNotBlank()) println(model.bornIn)
    if (model.diedIn.isNotBlank()) println(model.diedIn)
    if (model.biography.isNotBlank()) {
        println()
        println(model.biography)
    }
}

private fun printCredits(model: Details.Model) {
    println()
    println("${model.name} is Known for:")
    model.credits.forEach { credit ->
        val work = buildString {
            append(" - ${credit.title}")
            if (credit.year.isNotBlank()) append(", ${credit.year}")
            if (credit.credit.isNotBlank()) append(" - ${credit.credit}")
        }
        println(work)
    }
}
