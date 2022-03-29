package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.search.repository.SearchModel
import com.tutuland.wof.core.search.repository.cache.SearchCache

class SearchCacheForJs(private val log: Logger) : SearchCache {
    private val searchMap: HashMap<String, List<SearchModel>> = hashMapOf()

    override suspend fun cachedResultsFor(term: String): List<SearchModel> {
        return searchMap.getOrElse(term) { emptyList() }.also {
            log.d("Retrieved ${it.size} results for \"$term\" from the database")
        }
    }

    override suspend fun store(term: String, results: List<SearchModel>) {
        log.d("Inserting ${results.size} results for \"$term\" on the database")
        searchMap[term] = results
    }
}
