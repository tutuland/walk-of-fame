package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.details.data.local.DetailsCache

class DetailsCacheForJs(private val log: Logger) : DetailsCache {
    private val detailsMap: HashMap<String, DetailsModel> = hashMapOf()

    override suspend fun getDetailsFor(id: String): DetailsModel? {
        return detailsMap[id]?.also {
            log.d("Retrieved details for \"$id\" from the database")
        }
    }

    override suspend fun store(id: String, details: DetailsModel) {
        log.d("Inserting details for \"$id\" on the database")
        detailsMap[id] = details
    }
}
