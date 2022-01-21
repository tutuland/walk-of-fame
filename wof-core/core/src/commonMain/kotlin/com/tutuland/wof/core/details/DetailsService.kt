package com.tutuland.wof.core.details

import com.tutuland.wof.core.details.credits.Credits
import com.tutuland.wof.core.details.person.Person

class DetailsService(
    private val personApi: Person.Api,
    private val creditsApi: Credits.Api,
) : Details.Api {

    override suspend fun getDetailsFor(id: String): Details.Result {
        val person = personApi.getPersonFor(id)
        val credits = creditsApi.getCreditsFor(id)
        return Details.Result(person, credits)
    }
}
