package com.tutuland.wof.core

import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

fun injectOnAndroid(scope: CoroutineScope) {
    injectDependencies(
        module {
            single { scope }
            single { OkHttp.create() }
        }
    )
}
