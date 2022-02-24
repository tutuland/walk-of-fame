package com.tutuland.wof.core.details.service.cache

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.database.performTransactionOn
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.details.service.PersonService
import kotlinx.coroutines.CoroutineDispatcher

interface PersonCache {
    suspend fun cachedResultsFor(id: String): PersonService.Result?
    suspend fun store(id: String, result: PersonService.Result)

    class Impl(
        private val db: WofDb,
        private val log: Logger,
        private val backgroundDispatcher: CoroutineDispatcher,
    ) : PersonCache {
        override suspend fun cachedResultsFor(id: String): PersonService.Result? =
            db.detailsQueries
                .selectByIdFromPersonCache(id.toLong())
                .executeAsOneOrNull()
                ?.let { person ->
                    log.d("Retrieved a person result for \"$id\" from the database")
                    PersonService.Result(
                        id = person.id.toInt(),
                        name = person.name,
                        picturePath = person.picturePath,
                        department = person.department,
                        birthday = person.birthday,
                        deathday = person.deathday,
                        placeOfBirth = person.placeOfBirth,
                        biography = person.biography,
                    )
                }

        override suspend fun store(id: String, result: PersonService.Result) {
            log.d("Inserting a person result for \"$id\" into the database")
            db.performTransactionOn(backgroundDispatcher) {
                db.detailsQueries.insertPersonCache(
                    id = id.toLong(),
                    name = result.name.orEmpty(),
                    picturePath = result.picturePath,
                    department = result.department,
                    birthday = result.birthday,
                    deathday = result.deathday,
                    placeOfBirth = result.placeOfBirth,
                    biography = result.biography,
                )
            }
        }
    }
}
