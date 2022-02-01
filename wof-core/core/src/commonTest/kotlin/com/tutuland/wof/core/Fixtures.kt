package com.tutuland.wof.core

import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.api.CreditsApi
import com.tutuland.wof.core.details.api.PersonApi
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.api.SearchApi

const val fixId = 1
const val fixStringId = "$fixId"
const val fixName = "fixName"
const val fixPicturePath = "fixPicturePath"
const val fixPictureUrl = "https://image.tmdb.org/t/p/w500$fixPicturePath"
const val fixDepartment = "fixDepartment"
const val fixKnownForDepartment = "Known for $fixDepartment"
const val fixBirthday = "2014-01-10"
const val fixDeathday = "2099-12-31"
const val fixPlaceOfBirth = "Tutuland"
const val fixBornIn = "Born in January 10, 2014, Tutuland"
const val fixBornInUnknown = "Born in January 10, 2014, Unknown location"
const val fixDiedIn = "Died in December 31, 2099"
const val fixBiography = "fixBiography"
const val fixTitle = "fixTitle"
const val fixPosterPath = "fixPosterPath"
const val fixPosterUrl = "https://image.tmdb.org/t/p/w300$fixPosterPath"
const val fixJob = "fixJob"
const val fixCharacter = "fixCharacter"
const val fixAirDate = "2020-12-31"
const val fixAirYear = "2020"
const val fixReleaseDate = "2021-12-31"
const val fixReleaseYear = "2021"


val fixSearchPerson = SearchApi.Result.Person(
    id = fixId,
    name = fixName,
    department = fixDepartment,
)

val fixSearchModel = Search.Model(
    id = "$fixId",
    name = fixName,
    department = "Known for $fixDepartment",
)

val fixPersonApiResult = PersonApi.Result(
    id = fixId,
    name = fixName,
    picturePath = fixPicturePath,
    department = fixDepartment,
    birthday = fixBirthday,
    deathday = fixDeathday,
    placeOfBirth = fixPlaceOfBirth,
    biography = fixBiography,
)

val fixCreditsApiCrew = CreditsApi.Result.Work(
    id = fixId,
    title = fixTitle,
    posterPath = fixPosterPath,
    _job = fixJob,
    _character = null,
    _firstAirDate = fixAirDate,
    _releaseDate = null,
)

val fixCreditsApiCast = fixCreditsApiCrew.copy(
    _job = null,
    _character = fixCharacter,
    _firstAirDate = null,
    _releaseDate = fixReleaseDate,
)

val fixCreditsApiUncredited = fixCreditsApiCrew.copy(
    _job = null,
)

val fixCreditsApiResult = CreditsApi.Result(
    cast = listOf(fixCreditsApiCast),
    crew = listOf(fixCreditsApiCrew, fixCreditsApiUncredited),
)

val fixDetailsCreditModelCrew = Details.Model.Credit(
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

val fixDetailsModel = Details.Model(
    pictureUrl = fixPictureUrl,
    name = fixName,
    department = fixKnownForDepartment,
    bornIn = fixBornIn,
    diedIn = fixDiedIn,
    biography = fixBiography,
    credits = listOf(
        fixDetailsCreditModelCast,
        fixDetailsCreditModelCrew,
        fixDetailsCreditModelUncredited,
    ),
)
