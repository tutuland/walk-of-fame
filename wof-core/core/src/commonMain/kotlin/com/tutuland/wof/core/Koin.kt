package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.tutuland.wof.core.database.makeWofDbWith
import com.tutuland.wof.core.details.Details
import com.tutuland.wof.core.details.service.CreditsService
import com.tutuland.wof.core.details.service.PersonService
import com.tutuland.wof.core.details.service.api.CreditsApi
import com.tutuland.wof.core.details.service.api.PersonApi
import com.tutuland.wof.core.details.service.cache.CreditsCache
import com.tutuland.wof.core.details.service.cache.PersonCache
import com.tutuland.wof.core.details.usecase.RequestDetails
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.networking.makeHttpClient
import com.tutuland.wof.core.search.Search
import com.tutuland.wof.core.search.service.SearchService
import com.tutuland.wof.core.search.service.api.SearchApi
import com.tutuland.wof.core.search.service.cache.SearchCache
import com.tutuland.wof.core.search.usecase.SearchForPeople
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
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
    single<SearchService> { SearchService.Impl(get(), get()) }
    single<Search.ForPeople> { SearchForPeople(get()) }
    single { SearchViewModel(get(), get { parametersOf("$TAG SearchViewModel") }, get()) }

    single<PersonApi> { PersonApi.Impl(get()) }
    single<PersonCache> { PersonCache.Impl(get(), get { parametersOf("$TAG PersonCache") }, get()) }
    single<PersonService> { PersonService.Impl(get(), get()) }
    single<CreditsApi> { CreditsApi.Impl(get()) }
    single<CreditsCache> { CreditsCache.Impl(get(), get { parametersOf("$TAG CreditsCache") }, get()) }
    single<CreditsService> { CreditsService.Impl(get(), get()) }
    single<Details.Request> { RequestDetails(get(), get()) }
    single { DetailsViewModel(get(), get { parametersOf("$TAG DetailsViewModel") }, get()) }
}
