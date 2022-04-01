package com.tutuland.wof.core.details.data.remote

import com.tutuland.wof.core.mapDate
import com.tutuland.wof.core.mapPosterPictureFrom
import com.tutuland.wof.core.mapProfilePictureFrom
import com.tutuland.wof.core.mapYear
import com.tutuland.wof.core.details.data.DetailsModel

internal inline infix fun PersonPayload.mapToDetailsWith(credits: CreditsPayload): DetailsModel {
    if (isValid.not()) throw InvalidPersonResult
    return DetailsModel(
        id = id?.toString().orEmpty(),
        pictureUrl = mapProfilePictureFrom(picturePath),
        name = name.orEmpty(),
        department = mapDepartment(),
        bornIn = mapBornIn(),
        diedIn = mapDiedIn(),
        biography = biography.orEmpty(),
        credits = credits.mapCredits()
    )
}

internal inline fun PersonPayload.mapDepartment(): String = department
    ?.let { "Known for $it" }
    .orEmpty()

internal inline fun PersonPayload.mapBornIn(): String = birthday.mapDate()
    ?.let { "Born in $it, ${placeOfBirth ?: "Unknown location"}" }
    .orEmpty()

internal inline fun PersonPayload.mapDiedIn(): String = deathday.mapDate()
    ?.let { "Died in $it" }
    .orEmpty()

internal inline fun CreditsPayload.mapCredits(): List<DetailsModel.Credit> {
    val cast = cast.orEmpty()
        .filter { it.isValid }
        .map { it.mapToCredit { "Acting" } }
    val crew = crew.orEmpty()
        .filter { it.isValid }
        .map { it.mapToCredit { job ?: character ?: "uncredited" } }
    return cast + crew
}

internal inline fun CreditsPayload.Work.mapToCredit(credit: CreditsPayload.Work.() -> String) =
    DetailsModel.Credit(
        id = id?.toString().orEmpty(),
        posterUrl = mapPosterPictureFrom(posterPath),
        title = title.orEmpty(),
        credit = credit(),
        year = mapYear(),
    )

internal inline fun CreditsPayload.Work.mapYear(): String =
    (releaseDate ?: firstAirDate)
        .mapYear()
        .orEmpty()

internal inline val PersonPayload.isValid: Boolean get() = id != null && name.isNullOrBlank().not()

internal inline val CreditsPayload.Work.isValid: Boolean get() = id != null && title.isNullOrBlank().not()

object InvalidPersonResult : Exception("Invalid result for get person api.")
