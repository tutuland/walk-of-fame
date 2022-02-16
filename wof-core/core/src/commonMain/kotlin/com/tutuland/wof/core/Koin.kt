package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.api.CreditsApi
import com.tutuland.wof.core.details.api.PersonApi
import com.tutuland.wof.core.details.usecase.RequestDetails
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.api.SearchApi
import com.tutuland.wof.core.search.usecase.SearchForPeople
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
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

    single<SearchApi> { SearchApi.Impl(get()) }
    single<Search.ForPeople> { SearchForPeople(get()) }
    single { SearchViewModel(get(), get { parametersOf("$TAG SearchViewModel") }, get()) }

    single<PersonApi> { PersonApi.Impl(get()) }
    single<CreditsApi> { CreditsApi.Impl(get()) }
    single<Details.Request> { RequestDetails(get(), get()) }
    single { DetailsViewModel(get(), get { parametersOf("$TAG DetailsViewModel") }, get()) }
}
