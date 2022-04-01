package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.tutuland.wof.core.BuildKonfig.API_KEY
import com.tutuland.wof.core.BuildKonfig.BASE_URL
import com.tutuland.wof.core.details.data.DetailsModel
import com.tutuland.wof.core.details.data.remote.CreditsPayload
import com.tutuland.wof.core.details.data.remote.PersonPayload
import com.tutuland.wof.core.search.data.SearchModel
import com.tutuland.wof.core.search.data.remote.SearchPayload
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

const val fixId = 172069
const val fixStringId = "$fixId"
const val fixName = "Chadwick Boseman"
const val fixPicturePath = "/mXxiOTrTMJBRSVRfgaSDhOfvfxU.jpg"
const val fixPictureUrl = "https://image.tmdb.org/t/p/w500$fixPicturePath"
const val fixDepartment = "Acting"
const val fixKnownForDepartment = "Known for $fixDepartment"
const val fixBirthday = "1976-11-29"
const val fixDeathday = "2020-08-28"
const val fixPlaceOfBirth = "Anderson, South Carolina, USA"
const val fixBornIn = "Born in November 29, 1976, Anderson, South Carolina, USA"
const val fixBornInUnknown = "Born in November 29, 1976, Unknown location"
const val fixDiedIn = "Died in August 28, 2020"
const val fixBiography = "Chadwick Boseman was an American actor, playwright, and screenwriter hailing from Anderson, South Carolina. He graduated from Howard University and went on to study at the British American Dramatic Academy in Oxford.  Boseman's play \"Deep Azure\" was nominated for a 2006 Joseph Jefferson Award for New Work.  His breakout role was playing the lead Jackie Robinson in 2013's 42.\n\nBoseman was best remembered for portraying T’Challa/Black Panther in the Marvel Cinematic Universe. He has portrayed the character in Captain America: Civil War (2016), Black Panther (2018), Avengers: Infinity War (2018), and Avengers: Endgame (2019)."
const val fixCreditId = 392982
const val fixStringCreditId = "$fixCreditId"
const val fixTitle = "Marshall"
const val fixPosterPath = "/2KfdXsXTCbMie0wB1mSmIX60C2F.jpg"
const val fixPosterUrl = "https://image.tmdb.org/t/p/w300$fixPosterPath"
const val fixJob = "Co-Producer"
const val fixCharacter = "Thurgood Marshall"
const val fixAirDate = "2017-10-13"
const val fixAirYear = "2017"
const val fixReleaseDate = "2017-10-13"
const val fixReleaseYear = "2017"

val emptyLogger = Logger(config = StaticConfig(logWriterList = emptyList()))
fun makeUrl(path: String) = "$BASE_URL${path}api_key=$API_KEY"

fun MockRequestHandleScope.respond(content: String): HttpResponseData =
    respond(
        content = content,
        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
    )

fun MockRequestHandleScope.respondError(): HttpResponseData =
    respondError(
        status = HttpStatusCode.NotFound,
    )

val fixSearchRequest = makeUrl("search/person?query=$fixStringId&")
const val fixSearchResponse = """
{
  "page": 1,
  "results": [
    {
      "adult": false,
      "gender": 2,
      "id": 172069,
      "known_for": [
        {
          "adult": false,
          "backdrop_path": "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg",
          "genre_ids": [
            12,
            28,
            878
          ],
          "id": 299536,
          "media_type": "movie",
          "original_language": "en",
          "original_title": "Avengers: Infinity War",
          "overview": "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
          "poster_path": "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
          "release_date": "2018-04-25",
          "title": "Avengers: Infinity War",
          "video": false,
          "vote_average": 8.3,
          "vote_count": 23916
        },
        {
          "adult": false,
          "backdrop_path": "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
          "genre_ids": [
            12,
            878,
            28
          ],
          "id": 299534,
          "media_type": "movie",
          "original_language": "en",
          "original_title": "Avengers: Endgame",
          "overview": "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
          "poster_path": "/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
          "release_date": "2019-04-24",
          "title": "Avengers: Endgame",
          "video": false,
          "vote_average": 8.3,
          "vote_count": 20356
        },
        {
          "adult": false,
          "backdrop_path": "/7FWlcZq3r6525LWOcvO9kNWurN1.jpg",
          "genre_ids": [
            12,
            28,
            878
          ],
          "id": 271110,
          "media_type": "movie",
          "original_language": "en",
          "original_title": "Captain America: Civil War",
          "overview": "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
          "poster_path": "/rAGiXaUfPzY7CDEyNKUofk3Kw2e.jpg",
          "release_date": "2016-04-27",
          "title": "Captain America: Civil War",
          "video": false,
          "vote_average": 7.4,
          "vote_count": 19367
        }
      ],
      "known_for_department": "Acting",
      "name": "Chadwick Boseman",
      "popularity": 14.375,
      "profile_path": "/mXxiOTrTMJBRSVRfgaSDhOfvfxU.jpg"
    }
  ],
  "total_pages": 1,
  "total_results": 1
}
"""

val fixCreditsRequest = makeUrl("person/$fixStringId/combined_credits?")
const val fixCreditsResponse = """
{
  "cast": [
    {
      "original_language": "en",
      "original_title": "Marshall",
      "poster_path": "/2KfdXsXTCbMie0wB1mSmIX60C2F.jpg",
      "video": false,
      "vote_average": 7.3,
      "overview": "Thurgood Marshall, the first African-American Supreme Court Justice, battles through one of his career-defining cases.",
      "release_date": "2017-10-13",
      "vote_count": 480,
      "title": "Marshall",
      "adult": false,
      "backdrop_path": "/oZZeG88rulKn1i5Y2WoJqLCBoF6.jpg",
      "id": 392982,
      "genre_ids": [
        18
      ],
      "popularity": 14.176,
      "character": "Thurgood Marshall",
      "credit_id": "57111d23c3a36831e1003d9d",
      "order": 0,
      "media_type": "movie"
    }
  ],
  "crew": [
    {
      "original_language": "en",
      "original_title": "Marshall",
      "poster_path": "/2KfdXsXTCbMie0wB1mSmIX60C2F.jpg",
      "video": false,
      "vote_average": 7.3,
      "overview": "Thurgood Marshall, the first African-American Supreme Court Justice, battles through one of his career-defining cases.",
      "first_air_date": "2017-10-13",
      "vote_count": 480,
      "title": "Marshall",
      "adult": false,
      "backdrop_path": "/oZZeG88rulKn1i5Y2WoJqLCBoF6.jpg",
      "id": 392982,
      "genre_ids": [
        18
      ],
      "popularity": 14.176,
      "credit_id": "5f4ac63e74507d0032abd7b8",
      "department": "Production",
      "job": "Co-Producer",
      "media_type": "movie"
    },
    {
      "original_language": "en",
      "original_title": "Marshall",
      "poster_path": "/2KfdXsXTCbMie0wB1mSmIX60C2F.jpg",
      "video": false,
      "vote_average": 7.3,
      "overview": "Thurgood Marshall, the first African-American Supreme Court Justice, battles through one of his career-defining cases.",
      "first_air_date": "2017-10-13",
      "vote_count": 480,
      "title": "Marshall",
      "adult": false,
      "backdrop_path": "/oZZeG88rulKn1i5Y2WoJqLCBoF6.jpg",
      "id": 392982,
      "genre_ids": [
        18
      ],
      "popularity": 14.176,
      "credit_id": "5f4ac63e74507d0032abd7b8",
      "department": "Production",
      "media_type": "movie"
    }
  ],
  "id": 172069
}
"""

val fixPersonRequest = makeUrl("person/$fixStringId?")
const val fixPersonResponse = """
{
  "adult": false,
  "also_known_as": [
    "Chadwick Aaron Boseman",
    "Chad Boseman",
    "채드윅 보스만",
    "Τσάντγουικ Μπόουζμαν",
    "Чедвик Боузман",
    "Чедвік Боузман",
    "تشادويك بوسمان"
  ],
  "biography": "Chadwick Boseman was an American actor, playwright, and screenwriter hailing from Anderson, South Carolina. He graduated from Howard University and went on to study at the British American Dramatic Academy in Oxford.  Boseman's play \"Deep Azure\" was nominated for a 2006 Joseph Jefferson Award for New Work.  His breakout role was playing the lead Jackie Robinson in 2013's 42.\n\nBoseman was best remembered for portraying T’Challa/Black Panther in the Marvel Cinematic Universe. He has portrayed the character in Captain America: Civil War (2016), Black Panther (2018), Avengers: Infinity War (2018), and Avengers: Endgame (2019).",
  "birthday": "1976-11-29",
  "deathday": "2020-08-28",
  "gender": 2,
  "homepage": null,
  "id": 172069,
  "imdb_id": "nm1569276",
  "known_for_department": "Acting",
  "name": "Chadwick Boseman",
  "place_of_birth": "Anderson, South Carolina, USA",
  "popularity": 14.375,
  "profile_path": "/mXxiOTrTMJBRSVRfgaSDhOfvfxU.jpg"
}
"""

val fixSearchPersonPayload = SearchPayload.PersonPayload(
    id = fixId,
    name = fixName,
    department = fixDepartment,
)

val fixSearchResult = SearchModel(
    id = fixStringId,
    name = fixName,
    department = fixKnownForDepartment,
)

val fixPersonPayload = PersonPayload(
    id = fixId,
    name = fixName,
    picturePath = fixPicturePath,
    department = fixDepartment,
    birthday = fixBirthday,
    deathday = fixDeathday,
    placeOfBirth = fixPlaceOfBirth,
    biography = fixBiography,
)

val fixIncompletePersonPayload = PersonPayload(id = fixId, name = fixName)

val fixCreditsApiCrew = CreditsPayload.Work(
    id = fixCreditId,
    title = fixTitle,
    posterPath = fixPosterPath,
    job = fixJob,
    character = null,
    firstAirDate = fixAirDate,
    releaseDate = null,
)

val fixCreditsApiCast = fixCreditsApiCrew.copy(
    job = null,
    character = fixCharacter,
    firstAirDate = null,
    releaseDate = fixReleaseDate,
)

val fixCreditsApiUncredited = fixCreditsApiCrew.copy(
    job = null,
)

val fixCreditsPayload = CreditsPayload(
    cast = listOf(fixCreditsApiCast),
    crew = listOf(fixCreditsApiCrew, fixCreditsApiUncredited),
)

val fixDetailsCreditModelEmpty = DetailsModel.Credit(
    id = "",
    posterUrl = "",
    title = "",
    credit = "",
    year = "",
)

val fixDetailsCreditModelCrew = DetailsModel.Credit(
    id = fixStringCreditId,
    posterUrl = fixPosterUrl,
    title = fixTitle,
    credit = fixJob,
    year = fixAirYear,
)

val fixDetailsCreditModelCast = fixDetailsCreditModelCrew.copy(
    credit = "Acting",
    year = fixReleaseYear,
)

val fixDetailsCreditModelUncredited = fixDetailsCreditModelCrew.copy(
    credit = "uncredited",
)

val fixDetailModelCredits = listOf(
    fixDetailsCreditModelCast,
    fixDetailsCreditModelCrew,
    fixDetailsCreditModelUncredited,
)

val fixDetailsModel = DetailsModel(
    id = fixStringId,
    pictureUrl = fixPictureUrl,
    name = fixName,
    department = fixKnownForDepartment,
    bornIn = fixBornIn,
    diedIn = fixDiedIn,
    biography = fixBiography,
    credits = fixDetailModelCredits,
)

val fixIncompleteDetailsModel = DetailsModel(
    id = fixStringId,
    pictureUrl = "",
    name = fixName,
    department = "",
    bornIn = "",
    diedIn = "",
    biography = "",
    credits = emptyList(),
)