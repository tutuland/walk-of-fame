package com.tutuland.wof.core.details.service.cache

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.database.performTransactionOn
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.db.WorksCache
import com.tutuland.wof.core.details.service.CreditsService
import kotlinx.coroutines.CoroutineDispatcher

interface CreditsCache {
    suspend fun cachedResultsFor(id: String): CreditsService.Result?
    suspend fun store(id: String, result: CreditsService.Result)

    class Impl(
        private val db: WofDb,
        private val log: Logger,
        private val backgroundDispatcher: CoroutineDispatcher,
    ) : CreditsCache {
        override suspend fun cachedResultsFor(id: String): CreditsService.Result? =
            db.detailsQueries
                .selectByIdFromCreditsCache(id.toLong())
                .executeAsOneOrNull()
                ?.let { credits ->
                    val castList = credits.castIds.selectWorks()
                    val crewList = credits.crewIds.selectWorks()
                    log.d("Retrieved ${castList.size + crewList.size} credit results for \"$id\" from the database")
                    CreditsService.Result(
                        cast = castList,
                        crew = crewList,
                    )
                }

        override suspend fun store(id: String, result: CreditsService.Result) {
            val castList = result.cast
                ?.filter { it.isValid }
                .orEmpty()
            val crewList = result.crew
                ?.filter { it.isValid }
                .orEmpty()

            if (castList.isNotEmpty() || crewList.isNotEmpty()) cacheResultsFor(id, castList, crewList)
            else log.d("There are no valid credit results for \"$id\"")
        }

        private inline fun List<Int>.selectWorks(): List<CreditsService.Result.Work> =
            db.detailsQueries
                .selectByIdsFromWorksCache(map { it.toLong() })
                .executeAsList()
                .map { it.toWork() }

        private inline fun WorksCache.toWork() = CreditsService.Result.Work(
            id = id.toInt(),
            title = title,
            posterPath = posterPath,
            _job = job,
            _releaseDate = releaseDate,
        )

        private suspend inline fun cacheResultsFor(
            id: String,
            castList: List<CreditsService.Result.Work>,
            crewList: List<CreditsService.Result.Work>
        ) {
            log.d("Inserting ${castList.size + crewList.size} credit results for \"$id\" into the database")
            db.performTransactionOn(backgroundDispatcher) {
                db.detailsQueries.insertCreditsCache(
                    id = id.toLong(),
                    castIds = castList.map { it.id ?: 0 },
                    crewIds = crewList.map { it.id ?: 0 },
                )
                castList.forEach { it.insertWorksCache() }
                crewList.forEach { it.insertWorksCache() }
            }
        }

        private inline fun CreditsService.Result.Work.insertWorksCache() =
            db.detailsQueries.insertWorksCache(
                id = id?.toLong() ?: 0,
                title = title.orEmpty(),
                posterPath = posterPath,
                job = job,
                releaseDate = releaseDate,
            )
    }
}
