package com.tutuland.wof.core.database

import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.TransactionWithoutReturn
import com.squareup.sqldelight.db.SqlDriver
import com.tutuland.wof.core.db.CreditsCache
import com.tutuland.wof.core.db.SearchResultsCache
import com.tutuland.wof.core.db.WofDb
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

const val WOFDB = "WofDb"

val listOfIdsAdapter = object : ColumnAdapter<List<Int>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) listOf()
        else databaseValue.split(",").map { it.toInt() }

    override fun encode(value: List<Int>) = value.joinToString(separator = ",")
}

fun makeWofDbWith(driver: SqlDriver): WofDb = WofDb(
    driver = driver,
    SearchResultsCacheAdapter = SearchResultsCache.Adapter(listOfIdsAdapter),
    CreditsCacheAdapter = CreditsCache.Adapter(listOfIdsAdapter, listOfIdsAdapter),
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
