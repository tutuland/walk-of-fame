package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.tutuland.wof.core.db.WofDb
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun injectOnDesktop(scope: CoroutineScope, shouldDisableLogger: Boolean = false): KoinApplication =
    injectDependencies(
        module {
            single { scope }
            single { Java.create() }
            single<SqlDriver> {
                // For now using in memory database for JVM
                JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
                    .also { WofDb.Schema.create(it) }
            }
            if (shouldDisableLogger) {
                val emptyLogger = Logger(config = StaticConfig(logWriterList = emptyList()))
                factory { (_: String?) -> emptyLogger }
            }
        }
    )
