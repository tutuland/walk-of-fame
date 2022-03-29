package com.tutuland.wof.core.search.repository.api

import com.tutuland.wof.core.search.repository.SearchModel

internal inline val SearchPayload.PersonPayload.isValid: Boolean
    get() = id != null && name.isNullOrBlank().not()

internal inline fun SearchPayload.PersonPayload.mapToResult() = SearchModel(
    id = id?.toString().orEmpty(),
    name = name.orEmpty(),
    department = department?.let { "Known for $it" }.orEmpty(),
)

internal inline fun SearchPayload.mapToResults(): List<SearchModel> =
    people.orEmpty()
        .filter { it.isValid }
        .map { it.mapToResult() }
