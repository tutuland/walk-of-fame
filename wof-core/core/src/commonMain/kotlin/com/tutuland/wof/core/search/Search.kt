package com.tutuland.wof.core.search

import kotlinx.coroutines.flow.Flow

interface Search {
    interface Provider {
        fun withName(name: String): Flow<Model>
    }

    data class Model(
        val id: String,
        val name: String,
        val department: String,
    )
}
