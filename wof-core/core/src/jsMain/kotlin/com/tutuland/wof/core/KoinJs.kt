package com.tutuland.wof.core

import com.tutuland.wof.core.details.repository.cache.DetailsCache
import com.tutuland.wof.core.search.repository.cache.SearchCache
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
            single<DetailsCache> { DetailsCacheForJs(get { parametersOf("$TAG DetailsCacheForJs") }) }
        }
    )
}
