package com.tutuland.wof.core

import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.SearchApi

object ServiceLocator {
    val searchApi: Search.Api = SearchApi(makeHttpClient())
}