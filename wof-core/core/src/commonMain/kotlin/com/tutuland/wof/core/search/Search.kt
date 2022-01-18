package com.tutuland.wof.core.search

interface Search {
    interface Api {
        suspend fun searchFor(person: String): String
    }
}