package com.tutuland.wof.core.search.repository.cache

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.database.performTransactionOn
import com.tutuland.wof.core.db.SearchPersonCache
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.search.repository.SearchModel
import kotlinx.coroutines.CoroutineDispatcher

interface SearchCache {
    suspend fun cachedResultsFor(term: String): List<SearchModel>
    suspend fun store(term: String, results: List<SearchModel>)

    class Impl(
        private val db: WofDb,
        private val log: Logger,
        private val backgroundDispatcher: CoroutineDispatcher,
    ) : SearchCache {
        override suspend fun cachedResultsFor(term: String): List<SearchModel> {
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
            return cachedResults
        }

        override suspend fun store(term: String, results: List<SearchModel>) {
            log.d("Inserting ${results.size} results for \"$term\" into the database")
            db.performTransactionOn(backgroundDispatcher) {
                db.searchQueries.insertSearchResultsCache(
                    term = term,
                    ids = results.map { it.id.toInt() }
                )
                results.forEach { person ->
                    db.searchQueries.insertSearchPersonCache(
                        id = person.id.toLong(),
                        name = person.name,
                        department = person.department
                    )
                }
            }
        }

        private inline fun SearchPersonCache.toPerson() = SearchModel(
            id = "$id",
            name = name,
            department = department.orEmpty(),
        )
    }
}
