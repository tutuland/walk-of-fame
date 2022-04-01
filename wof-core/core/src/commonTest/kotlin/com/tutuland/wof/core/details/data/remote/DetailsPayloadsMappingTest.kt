package com.tutuland.wof.core.details.data.remote

import com.tutuland.wof.core.fixAirYear
import com.tutuland.wof.core.fixBornIn
import com.tutuland.wof.core.fixBornInUnknown
import com.tutuland.wof.core.fixCreditsApiCast
import com.tutuland.wof.core.fixCreditsApiCrew
import com.tutuland.wof.core.fixCreditsPayload
import com.tutuland.wof.core.fixDetailModelCredits
import com.tutuland.wof.core.fixDetailsCreditModelCrew
import com.tutuland.wof.core.fixDetailsCreditModelEmpty
import com.tutuland.wof.core.fixDetailsModel
import com.tutuland.wof.core.fixDiedIn
import com.tutuland.wof.core.fixIncompleteDetailsModel
import com.tutuland.wof.core.fixIncompletePersonPayload
import com.tutuland.wof.core.fixJob
import com.tutuland.wof.core.fixKnownForDepartment
import com.tutuland.wof.core.fixPersonPayload
import com.tutuland.wof.core.fixReleaseYear
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DetailsPayloadsMappingTest {
    @Test
    fun mapToDetailsWith_handles_valid_payloads_properly() {
        assertEquals(fixDetailsModel, fixPersonPayload mapToDetailsWith fixCreditsPayload)
    }

    @Test
    fun mapToDetailsWith_handles_incomplete_payloads_properly() {
        assertEquals(fixIncompleteDetailsModel, fixIncompletePersonPayload mapToDetailsWith CreditsPayload())
    }

    @Test
    fun mapToDetailsWith_throws_for_invalid_person() {
        assertFailsWith<InvalidPersonResult> { PersonPayload() mapToDetailsWith fixCreditsPayload }
    }

    @Test
    fun personPayload_mapDepartment_handles_valid_payloads_properly() {
        assertEquals(fixKnownForDepartment, fixPersonPayload.mapDepartment())
    }

    @Test
    fun personPayload_mapDepartment_handles_empty_payloads_properly() {
        assertEquals("", PersonPayload().mapDepartment())
    }

    @Test
    fun personPayload_mapBornIn_handles_valid_payloads_properly() {
        assertEquals(fixBornIn, fixPersonPayload.mapBornIn())
    }

    @Test
    fun personPayload_mapBornIn_handles_payloads_without_placeOfBirth_properly() {
        assertEquals(fixBornInUnknown, fixPersonPayload.copy(placeOfBirth = null).mapBornIn())
    }

    @Test
    fun personPayload_mapBornIn_handles_empty_payloads_properly() {
        assertEquals("", PersonPayload().mapBornIn())
    }

    @Test
    fun personPayload_mapDiedIn_handles_valid_payloads_properly() {
        assertEquals(fixDiedIn, fixPersonPayload.mapDiedIn())
    }

    @Test
    fun personPayload_mapDiedIn_handles_empty_payloads_properly() {
        assertEquals("", PersonPayload().mapDiedIn())
    }

    @Test
    fun creditsPayload_mapCredits_handles_valid_payloads_properly() {
        assertEquals(fixDetailModelCredits, fixCreditsPayload.mapCredits())
    }

    @Test
    fun creditsPayload_mapCredits_handles_empty_payloads_properly() {
        assertEquals(emptyList(), CreditsPayload().mapCredits())
    }

    @Test
    fun creditsPayloadWork_mapToCredit_handles_valid_payloads_properly() {
        assertEquals(fixDetailsCreditModelCrew, fixCreditsApiCrew.mapToCredit { fixJob })
    }

    @Test
    fun creditsPayloadWork_mapToCredit_handles_empty_payloads_properly() {
        assertEquals(fixDetailsCreditModelEmpty, CreditsPayload.Work().mapToCredit { "" })
    }

    @Test
    fun creditsPayloadWork_mapYear_uses_releaseDate_if_present() {
        assertEquals(fixReleaseYear, fixCreditsApiCast.mapYear())
    }

    @Test
    fun creditsPayloadWork_mapYear_uses_airDate_if_present() {
        assertEquals(fixAirYear, fixCreditsApiCrew.mapYear())
    }

    @Test
    fun creditsPayloadWork_mapYear_maps_to_empty_if_no_date_is_present() {
        assertEquals("", CreditsPayload.Work().mapYear())
    }

    @Test
    fun personPayload_isValid_returns_false_for_null_id() {
        assertFalse { fixPersonPayload.copy(id = null).isValid }
    }

    @Test
    fun personPayload_isValid_returns_false_for_null_name() {
        assertFalse { fixPersonPayload.copy(name = null).isValid }
    }

    @Test
    fun personPayload_isValid_returns_false_for_empty_name() {
        assertFalse { fixPersonPayload.copy(name = " ").isValid }
    }

    @Test
    fun personPayload_isValid_returns_true_for_valid_payloads() {
        assertTrue { fixPersonPayload.isValid }
    }

    @Test
    fun creditsPayloadWork_isValid_returns_false_for_null_id() {
        assertFalse { fixCreditsApiCrew.copy(id = null).isValid }
    }

    @Test
    fun creditsPayloadWork_isValid_returns_false_for_null_name() {
        assertFalse { fixCreditsApiCrew.copy(title = null).isValid }
    }

    @Test
    fun creditsPayloadWork_isValid_returns_false_for_empty_name() {
        assertFalse { fixCreditsApiCrew.copy(title = " ").isValid }
    }

    @Test
    fun creditsPayloadWork_isValid_returns_true_for_valid_payloads() {
        assertTrue { fixCreditsApiCrew.isValid }
    }
}
