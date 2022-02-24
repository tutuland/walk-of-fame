package com.tutuland.wof.core

import com.tutuland.wof.core.search.service.cache.SearchCache
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineScope
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun injectOnJs(scope: CoroutineScope) {
    injectDependencies(
        module {
            single { scope }
            single { Js.create() }
            single<SearchCache> { SearchCacheForJs(get { parametersOf("$TAG SearchCacheForJs") }) }
        }
    )
}
