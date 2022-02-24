package com.tutuland.wof.core.search.service.cache

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.database.performTransactionOn
import com.tutuland.wof.core.db.SearchPersonCache
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.search.service.SearchService
import kotlinx.coroutines.CoroutineDispatcher

interface SearchCache {
    suspend fun cachedResultsFor(term: String): SearchService.Result
    suspend fun store(term: String, result: SearchService.Result)

    class Impl(
        private val db: WofDb,
        private val log: Logger,
        private val backgroundDispatcher: CoroutineDispatcher,
    ) : SearchCache {
        override suspend fun cachedResultsFor(term: String): SearchService.Result {
            val resultIds = db.searchQueries
                .selectIdsByTermFromSearchResultsCache(term)
                .executeAsOneOrNull()
                .orEmpty()
                .map { it.toLong() }

            val cachedResults = db.searchQueries
                .selectAllByIdFromSearchPersonCache(resultIds)
                .executeAsList()
                .map { it.toPerson() }

            log.d("Retrieved ${cachedResults.size} results for \"$term\" from the database")
            return SearchService.Result(people = cachedResults)
        }

        override suspend fun store(term: String, result: SearchService.Result) {
            result.people
                ?.filter { it.isValid }
                ?.cacheResultsFor(term)
        }

        private inline fun SearchPersonCache.toPerson() = SearchService.Result.Person(
            id = id.toInt(),
            name = name,
            department = department,
        )

        private suspend inline fun List<SearchService.Result.Person>.cacheResultsFor(term: String) {
            log.d("Inserting $size results for \"$term\" into the database")
            db.performTransactionOn(backgroundDispatcher) {
                db.searchQueries.insertSearchResultsCache(
                    term = term,
                    ids = map { it.id ?: 0 }
                )
                forEach { person ->
                    db.searchQueries.insertSearchPersonCache(
                        id = person.id?.toLong() ?: 0,
                        name = person.name.orEmpty(),
                        department = person.department.orEmpty()
                    )
                }
            }
        }
    }
}
