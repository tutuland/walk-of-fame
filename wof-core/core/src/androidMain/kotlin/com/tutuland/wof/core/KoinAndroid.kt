package com.tutuland.wof.core

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.tutuland.wof.core.database.WOFDB
import com.tutuland.wof.core.db.WofDb
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

fun injectOnAndroid(scope: CoroutineScope, context: Context) {
    injectDependencies(
        module {
            single { scope }
            single { OkHttp.create() }
            single<SqlDriver> { AndroidSqliteDriver(WofDb.Schema, context, WOFDB) }
        }
    )
}
