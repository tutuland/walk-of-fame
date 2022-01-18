package com.tutuland.wof.core.search

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.MainScope
import com.tutuland.wof.core.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NativeSearchApi {
    private val api = ServiceLocator.searchApi
    private val scope = MainScope(Dispatchers.Main)

    fun searchFor(person: String, callback: (String) -> Unit) {
        scope.launch {
            runCatching {
                api.searchFor(person)
            }.onSuccess { result ->
                Logger.d("Success!")
                callback(result)
            }.onFailure {
                Logger.d("Failure!\n--------\n$it")
                callback("")
            }
        }
    }

    fun onDestroy() {
        scope.onDestroy()
    }
}