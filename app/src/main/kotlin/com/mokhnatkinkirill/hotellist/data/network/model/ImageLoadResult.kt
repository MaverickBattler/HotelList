package com.mokhnatkinkirill.hotellist.data.network.model

sealed interface ImageLoadResult {

    data object FailedToLoad : ImageLoadResult

    data object ImageNotFound : ImageLoadResult

    data class Image(val path: String) : ImageLoadResult
}