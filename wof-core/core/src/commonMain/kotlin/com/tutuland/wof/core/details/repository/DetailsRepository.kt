package com.tutuland.wof.core.details.repository

import com.tutuland.wof.core.details.repository.api.DetailsApi
import com.tutuland.wof.core.details.repository.cache.DetailsCache

interface DetailsRepository {
    suspend fun getDetailsFor(id: String): DetailsModel

    class Impl(
        private val api: DetailsApi,
        private val cache: DetailsCache,
    ) : DetailsRepository {
        override suspend fun getDetailsFor(id: String): DetailsModel =
            cache.getDetailsFor(id) ?: fetchDetailsAndAddToCache(id)

        private suspend inline fun fetchDetailsAndAddToCache(id: String): DetailsModel {
            val details = api.getDetailsFor(id)
            cache.store(id, details)
            return details
        }
    }
}
