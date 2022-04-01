package com.tutuland.wof.core.details.data.local

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.database.performTransactionOn
import com.tutuland.wof.core.db.CreditsCache
import com.tutuland.wof.core.db.PersonCache
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.details.data.DetailsModel
import kotlinx.coroutines.CoroutineDispatcher

interface DetailsCache {
    suspend fun getDetailsFor(id: String): DetailsModel?
    suspend fun store(id: String, details: DetailsModel)

    class Impl(
        private val db: WofDb,
        private val log: Logger,
        private val backgroundDispatcher: CoroutineDispatcher,
    ) : DetailsCache {
        override suspend fun getDetailsFor(id: String): DetailsModel? =
            cachedPersonFor(id)?.let { person ->
                val credits = cachedCreditsFor(id, person.creditIds)
                person mapToDetailsWith credits
            }

        override suspend fun store(id: String, details: DetailsModel) {
            log.d("Inserting a person result for \"$id\" into the database")
            db.performTransactionOn(backgroundDispatcher) {
                details.insertPersonCache()
                details.credits.forEach { it.insertWorksCache() }
            }
        }

        private inline fun cachedPersonFor(id: String): PersonCache? =
            db.detailsQueries
                .selectByIdFromPersonCache(id)
                .executeAsOneOrNull()
                ?.also { log.d("Retrieved a person result for \"${it.id}\" from the database") }

        private inline fun cachedCreditsFor(id: String, creditIds: List<String>): List<CreditsCache> =
            db.detailsQueries
                .selectByIdsFromCreditsCache(creditIds)
                .executeAsList()
                .also { log.d("Retrieved ${it.size} credit results for \"$id\" from the database") }

        private inline fun DetailsModel.insertPersonCache() =
            db.detailsQueries.insertPersonCache(
                id = id,
                name = name,
                pictureUrl = pictureUrl,
                department = department,
                bornIn = bornIn,
                diedIn = diedIn,
                biography = biography,
                creditIds = credits.map { it.id },
            )

        private inline fun DetailsModel.Credit.insertWorksCache() =
            db.detailsQueries.insertCreditsCache(
                id = id,
                title = title,
                posterUrl = posterUrl,
                credit = credit,
                year = year,
            )

        private inline infix fun PersonCache?.mapToDetailsWith(credits: List<CreditsCache>): DetailsModel? =
            this?.let { person ->
                DetailsModel(
                    id = person.id,
                    pictureUrl = person.pictureUrl,
                    name = person.name,
                    department = person.department,
                    bornIn = person.bornIn,
                    diedIn = person.diedIn,
                    biography = person.biography,
                    credits = credits.map { it.mapToCredit() },
                )
            }

        private inline fun CreditsCache.mapToCredit() = DetailsModel.Credit(
            id = id,
            posterUrl = posterUrl,
            title = title,
            credit = credit,
            year = year,
        )
    }
}
