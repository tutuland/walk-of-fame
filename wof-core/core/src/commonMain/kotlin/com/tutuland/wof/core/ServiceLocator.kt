package com.tutuland.wof.core

import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.DetailsService
import com.tutuland.wof.core.details.credits.Credits
import com.tutuland.wof.core.details.credits.CreditsService
import com.tutuland.wof.core.details.person.Person
import com.tutuland.wof.core.details.person.PersonService
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.SearchService

object ServiceLocator {
    private val client = makeHttpClient()
    private val personApi: Person.Api = PersonService(client)
    private val creditsApi: Credits.Api = CreditsService(client)
    val detailsApi: Details.Api = DetailsService(personApi, creditsApi)
    val searchApi: Search.Api = SearchService(client)

}