package com.tutuland.wof.core.search

import co.touchlab.kermit.Logger
import com.tutuland.wof.core.ServiceLocator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class JsSearchApi {
    private val api = ServiceLocator.searchApi

    fun searchFor(person: String, callback: (String) -> Unit) {
        GlobalScope.launch {
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
}