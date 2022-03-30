package com.tutuland.wof.core.database

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.TransactionWithoutReturn
import com.squareup.sqldelight.db.SqlDriver
import com.tutuland.wof.core.db.PersonCache
import com.tutuland.wof.core.db.SearchResultsCache
import com.tutuland.wof.core.db.WofDb
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

const val WOFDB = "WofDb_v2"

val listOfIdsAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) listOf()
        else databaseValue.split(",")

    override fun encode(value: List<String>) = value.joinToString(separator = ",")
}

fun makeWofDbWith(driver: SqlDriver): WofDb = WofDb(
    driver = driver,
    SearchResultsCacheAdapter = SearchResultsCache.Adapter(listOfIdsAdapter),
    PersonCacheAdapter = PersonCache.Adapter(listOfIdsAdapter),
)

suspend fun Transacter.performTransactionOn(
    coroutineContext: CoroutineContext,
    noEnclosing: Boolean = false,
    body: TransactionWithoutReturn.() -> Unit
) {
    withContext(coroutineContext) {
        this@performTransactionOn.transaction(noEnclosing) {
            body()
        }
    }
}
