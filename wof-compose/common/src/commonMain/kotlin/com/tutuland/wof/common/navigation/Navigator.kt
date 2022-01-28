package com.tutuland.wof.common.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow

typealias Screen = @Composable () -> Unit

fun <T> MutableList<T>.push(item: T) = add(count(), item)
fun <T> MutableList<T>.pop(): T? = if (count() > 0) removeAt(count() - 1) else null
fun <T> MutableList<T>.peek(): T? = if (count() > 0) this[count() - 1] else null

interface Navigator {
    val currentScreen: StateFlow<Screen>
    fun goToSearch()
    fun goToDetailsFor(id: String)
    fun goToFullBio()
    fun goBack(): Boolean
}
