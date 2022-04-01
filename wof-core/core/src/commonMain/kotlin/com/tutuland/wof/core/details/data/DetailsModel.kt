package com.tutuland.wof.core.details.data

data class DetailsModel(
    val id: String,
    val pictureUrl: String,
    val name: String,
    val department: String,
    val bornIn: String,
    val diedIn: String,
    val biography: String,
    val credits: List<Credit>,
) {
    data class Credit(
        val id: String,
        val posterUrl: String,
        val title: String,
        val credit: String,
        val year: String
    )
}
