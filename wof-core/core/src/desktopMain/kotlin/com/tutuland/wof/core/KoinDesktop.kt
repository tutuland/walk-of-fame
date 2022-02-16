package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import io.ktor.client.engine.java.Java
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun injectOnDesktop(scope: CoroutineScope, shouldDisableLogger: Boolean = false): KoinApplication =
    injectDependencies(
        module {
            single { scope }
            single { Java.create() }
            if (shouldDisableLogger) {
                val emptyLogger = Logger(config = StaticConfig(logWriterList = emptyList()))
                factory { (_: String?) -> emptyLogger }
            }
        }
    )
