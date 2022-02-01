package com.tutuland.wof.android

import android.app.Application
import com.tutuland.wof.core.details.viewmodel.DetailsViewModel
import com.tutuland.wof.core.search.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class WofApplication : Application() {
    companion object {
        lateinit var scope: CoroutineScope
        lateinit var searchViewModel: SearchViewModel
        lateinit var detailsViewModel: DetailsViewModel
    }

    override fun onCreate() {
        super.onCreate()
        scope = MainScope()
        searchViewModel = SearchViewModel(scope)
        detailsViewModel = DetailsViewModel(scope)
    }

    override fun onTerminate() {
        scope.cancel()
        super.onTerminate()
    }
}
