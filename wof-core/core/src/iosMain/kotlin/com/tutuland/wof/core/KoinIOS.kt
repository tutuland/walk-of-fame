package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.tutuland.wof.core.database.WOFDB
import com.tutuland.wof.core.db.WofDb
import com.tutuland.wof.core.details.viewmodel.NativeDetailsViewModel
import com.tutuland.wof.core.search.viewmodel.NativeSearchViewModel
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun injectOnIOS(): KoinApplication =
    injectDependencies(
        module {
            single<CoroutineScope> { MainScope(Dispatchers.Main, get { parametersOf("$TAG MainScope") }) }
            single<SqlDriver> { NativeSqliteDriver(WofDb.Schema, WOFDB) }
            single { Darwin.create() }
            single { NativeSearchViewModel(get(), get()) }
            single { NativeDetailsViewModel(get(), get()) }
        }
    )

fun Koin.getLoggerWith(tag: String): Logger = get { parametersOf("$TAG $tag") }
fun Koin.getSearchViewModel(): NativeSearchViewModel = get()
fun Koin.getDetailsViewModel(): NativeDetailsViewModel = get()
