package com.tutuland.wof.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

private const val POSTER_URL = "https://image.tmdb.org/t/p/w300"
private const val PROFILE_URL = "https://image.tmdb.org/t/p/w500"

fun mapPosterPictureFrom(path: String?): String = path?.let { POSTER_URL + it }.orEmpty()
fun mapProfilePictureFrom(path: String?): String = path?.let { PROFILE_URL + it }.orEmpty()

fun String.titlecase() = lowercase().replaceFirstChar { it.titlecase() }
fun String?.mapDate(): String? = transformDate { "${month.name.titlecase()} $dayOfMonth, $year" }
fun String?.mapYear(): String? = transformDate { "$year" }
fun String?.transformDate(transform: LocalDate.() -> String): String? = try {
    this?.toLocalDate()?.run(transform)
} catch (error: Throwable) {
    null
}
