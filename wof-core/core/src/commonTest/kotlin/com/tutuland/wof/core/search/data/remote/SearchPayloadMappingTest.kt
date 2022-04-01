package com.tutuland.wof.core.search.data.remote

import com.tutuland.wof.core.fixSearchPersonPayload
import com.tutuland.wof.core.fixSearchResult
import com.tutuland.wof.core.search.data.SearchModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SearchPayloadMappingTest {
    @Test
    fun isValid_returns_false_for_null_id() {
        assertFalse { fixSearchPersonPayload.copy(id = null).isValid }
    }

    @Test
    fun isValid_returns_false_for_null_name() {
        assertFalse { fixSearchPersonPayload.copy(name = null).isValid }
    }

    @Test
    fun isValid_returns_false_for_empty_name() {
        assertFalse { fixSearchPersonPayload.copy(name = " ").isValid }
    }

    @Test
    fun isValid_returns_true_for_valid_payloads() {
        assertTrue { fixSearchPersonPayload.isValid }
    }

    @Test
    fun mapToResult_maps_empty_payloads() {
        assertEquals(SearchModel("", "", ""), SearchPayload.PersonPayload().mapToResult())
    }

    @Test
    fun mapToResult_maps_valid_payloads() {
        assertEquals(fixSearchResult, fixSearchPersonPayload.mapToResult())
    }

    @Test
    fun mapToResults_handles_empty_payloads_properly() {
        val payload = SearchPayload()
        assertEquals(listOf(), payload.mapToResults())
    }

    @Test
    fun mapToResults_handles_valid_payloads_properly() {
        val payload = SearchPayload(
            listOf(
                fixSearchPersonPayload,
            )
        )
        assertEquals(listOf(fixSearchResult), payload.mapToResults())
    }

    @Test
    fun mapToResults_filters_invalid_payloads_properly() {
        val payload = SearchPayload(
            listOf(
                fixSearchPersonPayload.copy(id = null),
                fixSearchPersonPayload,
                fixSearchPersonPayload.copy(name = null),
            )
        )
        assertEquals(listOf(fixSearchResult), payload.mapToResults())
    }
}
