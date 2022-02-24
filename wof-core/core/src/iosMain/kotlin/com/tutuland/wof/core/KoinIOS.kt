package com.tutuland.wof.core

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.tutuland.wof.core.database.WOFDB
import com.tutuland.wof.core.db.WofDb
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun injectOnIOS() {
    injectDependencies(
        module {
            single<CoroutineScope> { MainScope(Dispatchers.Main, get()) }
            single<SqlDriver> { NativeSqliteDriver(WofDb.Schema, WOFDB) }
            single { Darwin.create() }
        }
    )
}
