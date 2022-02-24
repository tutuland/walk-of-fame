package com.tutuland.wof.core.details.usecase

import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.mapDate
import com.tutuland.wof.core.details.mapPosterPictureFrom
import com.tutuland.wof.core.details.mapProfilePictureFrom
import com.tutuland.wof.core.details.mapYear
import com.tutuland.wof.core.details.service.CreditsService
import com.tutuland.wof.core.details.service.PersonService

class RequestDetails(
    private val personService: PersonService,
    private val creditsService: CreditsService,
) : Details.Request {

    override suspend fun with(id: String): Details.Model {
        val personResult = getPersonFor(id)
        val creditsResult = creditsService.getCreditsFor(id)
        return Details.Model(
            pictureUrl = mapProfilePictureFrom(personResult.picturePath),
            name = personResult.name.orEmpty(),
            department = personResult.mapDepartment(),
            bornIn = personResult.mapBornIn(),
            diedIn = personResult.mapDiedIn(),
            biography = personResult.biography.orEmpty(),
            credits = creditsResult.mapCredits()
        )
    }

    private suspend fun getPersonFor(id: String): PersonService.Result {
        val person = personService.getPersonFor(id)
        if (person.isValid.not()) throw InvalidPersonResult
        return person
    }

    private fun PersonService.Result.mapDepartment(): String = department
        ?.let { "Known for $it" }
        .orEmpty()

    private fun PersonService.Result.mapBornIn(): String = birthday.mapDate()
        ?.let { "Born in $it, ${placeOfBirth ?: "Unknown location"}" }
        .orEmpty()

    private fun PersonService.Result.mapDiedIn(): String = deathday.mapDate()
        ?.let { "Died in $it" }
        .orEmpty()

    private fun CreditsService.Result.mapCredits(): List<Details.Model.Credit> {
        val cast = cast.orEmpty()
            .filter { it.isValid }
            .map { it.mapToCredit { "Acting" } }
        val crew = crew.orEmpty()
            .filter { it.isValid }
            .map { it.mapToCredit { job ?: "uncredited" } }
        return cast + crew
    }

    private fun CreditsService.Result.Work.mapToCredit(credit: CreditsService.Result.Work.() -> String) =
        Details.Model.Credit(
            posterUrl = mapPosterPictureFrom(posterPath),
            title = title.orEmpty(),
            credit = credit(),
            year = releaseDate.mapYear().orEmpty(),
        )
}

object InvalidPersonResult : Exception("Invalid result for get person api.")
