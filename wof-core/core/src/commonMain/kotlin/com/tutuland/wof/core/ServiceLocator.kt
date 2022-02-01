package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.api.CreditsApi
import com.tutuland.wof.core.details.api.PersonApi
import com.tutuland.wof.core.details.usecase.RequestDetails
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.api.SearchApi
import com.tutuland.wof.core.search.usecase.SearchForPeople

object ServiceLocator {
    val log = Logger.withTag("WoF")
    private val client = makeHttpClient(log)
    private val personApi: PersonApi = PersonApi.Impl(client)
    private val creditsApi: CreditsApi = CreditsApi.Impl(client)
    val requestDetails: Details.Provider = RequestDetails(personApi, creditsApi)
    private val searchApi: SearchApi = SearchApi.Impl(client)
    val searchForPeople: Search.Provider = SearchForPeople(searchApi)
}
