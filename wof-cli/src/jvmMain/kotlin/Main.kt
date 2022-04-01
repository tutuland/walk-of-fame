import com.github.kinquirer.KInquirer
import com.github.kinquirer.components.promptInput
import com.github.kinquirer.components.promptListObject
import com.github.kinquirer.core.Choice
import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.details.data.DetailsRepository
import com.tutuland.wof.core.injectOnDesktop
import com.tutuland.wof.core.search.data.SearchRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
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
    val searchRepository: SearchRepository = dependencies.get()
    val detailsRepository: DetailsRepository = dependencies.get()
    runBlocking {
        println(HEADER)
        searchForId(searchRepository)?.let { id -> detailsFor(id, detailsRepository) }
        println("\n ~ The End ~")
    }
    scope.cancel()
}

private suspend fun searchForId(searchRepository: SearchRepository): String? {
    val name = KInquirer.promptInput(message = "Who are you looking for?")
    val result = searchRepository
        .searchFor(name)
        .catch { }
        .firstOrNull()
        .orEmpty()

    if (result.isEmpty()) {
        println("\nNo results for: $name")
        return null
    }

    val choices = result.map { Choice(it.name, it) }
    val person = KInquirer.promptListObject(
        message = "You mean:",
        choices = choices,
        hint = "press Enter to pick",
        pageSize = 5,
    )
    return person.id
}

private suspend fun detailsFor(id: String, detailsRepository: DetailsRepository) {
    val model = detailsRepository.getDetailsFor(id)
    val option = KInquirer.promptListObject(
        message = "Are you interested in:",
        choices = listOf(
            Choice("Biography") { printFullBio(model) },
            Choice("Works") { printCredits(model) },
        ),
        hint = "press Enter to pick",
    )
    option()
}

private fun printFullBio(model: DetailsModel) {
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

private fun printCredits(model: DetailsModel) {
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
