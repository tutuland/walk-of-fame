package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.search.service.SearchService
import com.tutuland.wof.core.search.service.cache.SearchCache

class SearchCacheForJs(private val log: Logger) : SearchCache {
    private val searchMap: HashMap<String, SearchService.Result> = hashMapOf()

    override suspend fun cachedResultsFor(term: String): SearchService.Result {
        return searchMap.getOrElse(term) { SearchService.Result() }.also {
            log.d("Retrieved ${it.people.orEmpty().size} results for \"$term\" from the database")
        }
    }

    override suspend fun store(term: String, result: SearchService.Result) {
        log.d("Inserting ${result.people.orEmpty().size} results for \"$term\" on the database")
        searchMap[term] = result
    }
}
