package com.tutuland.wof.core

import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

fun injectOnJs(scope: CoroutineScope) {
    injectDependencies(
        module {
            single { scope }
            single { Js.create() }
        }
    )
}
