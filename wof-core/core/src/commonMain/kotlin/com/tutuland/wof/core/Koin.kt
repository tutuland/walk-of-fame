package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.tutuland.wof.core.database.makeWofDbWith
import com.tutuland.wof.core.details.data.DetailsRepository
import com.tutuland.wof.core.details.data.remote.DetailsApi
import com.tutuland.wof.core.details.data.local.DetailsCache
import com.tutuland.wof.core.details.ui.DetailsViewModel
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.data.SearchRepository
import com.tutuland.wof.core.search.data.remote.SearchApi
import com.tutuland.wof.core.search.data.local.SearchCache
import com.tutuland.wof.core.search.ui.SearchViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

internal fun injectDependencies(appDependencies: Module): KoinApplication =
    startKoin {
        modules(
            coreDependencies,
            appDependencies, // declare appDependencies later so they can override dependencies
        )
    }

internal const val TAG = "WoF"

internal val coreDependencies = module {
    val baseLogger = Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), TAG)
    factory { (tag: String?) -> baseLogger.withTag(tag ?: TAG) }

    single { makeHttpClient(get(), get { parametersOf("$TAG HttpClient") }) }

    factory { Dispatchers.Default }
    single { makeWofDbWith(get()) }

    single<SearchApi> { SearchApi.Impl(get()) }
    single<SearchCache> { SearchCache.Impl(get(), get { parametersOf("$TAG SearchCache") }, get()) }
    single<SearchRepository> { SearchRepository.Impl(get(), get()) }
    single { SearchViewModel(get(), get { parametersOf("$TAG SearchViewModel") }, get()) }

    single<DetailsApi> { DetailsApi.Impl(get()) }
    single<DetailsCache> { DetailsCache.Impl(get(), get { parametersOf("$TAG DetailsCache") }, get()) }
    single<DetailsRepository> { DetailsRepository.Impl(get(), get()) }
    single { DetailsViewModel(get(), get { parametersOf("$TAG DetailsViewModel") }, get()) }
}
