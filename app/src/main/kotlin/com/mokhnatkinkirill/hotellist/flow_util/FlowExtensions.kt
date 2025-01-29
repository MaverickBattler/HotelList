package com.mokhnatkinkirill.hotellist.flow_util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    collector: suspend (T) -> Unit
) {
    flowWithLifecycle(lifecycleOwner.lifecycle)
        .onEach(collector)
        .launchIn(lifecycleOwner.lifecycleScope)
}
