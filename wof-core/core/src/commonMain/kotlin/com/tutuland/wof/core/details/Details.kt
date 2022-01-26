package com.tutuland.wof.core.details

interface Details {
    interface Provider {
        suspend fun with(id: String): Model
    }

    data class Model(
        val pictureUrl: String,
        val name: String,
        val department: String,
        val bornIn: String,
        val diedIn: String,
        val biography: String,
        val credits: List<Credit>,
    ) {
        data class Credit(
            val posterUrl: String,
            val title: String,
            val credit: String,
            val year: String
        )
    }
}

