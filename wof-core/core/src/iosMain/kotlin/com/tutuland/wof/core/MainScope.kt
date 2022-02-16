package com.tutuland.wof.core

import co.touchlab.kermit.Logger
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainScope(private val mainContext: CoroutineContext, private val log: Logger) :
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = mainContext + job + exceptionHandler

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        showError(throwable)
    }

    private fun showError(t: Throwable) {
        log.e(throwable = t) { "Error in MainScope" }
    }

    fun onDestroy() {
        job.cancel()
    }
}
