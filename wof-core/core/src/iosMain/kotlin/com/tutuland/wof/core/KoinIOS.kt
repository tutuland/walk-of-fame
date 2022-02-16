package com.tutuland.wof.core

import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun injectOnIOS() {
    injectDependencies(
        module {
            single<CoroutineScope> { MainScope(Dispatchers.Main, get()) }
            single { Darwin.create() }
        }
    )
}
