package com.mokhnatkinkirill.hotellist.details.presentation.mapper

import com.mokhnatkinkirill.hotellist.data.network.model.ImageLoadResult
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState

class HotelDetailsImageDownloadUiStateMapper {

    fun mapHotelDetailsUiState(
        imageLoadResult: ImageLoadResult,
        prevUiState: HotelDetailsUiState
    ): HotelDetailsUiState {
        return if (prevUiState is HotelDetailsUiState.Content) {
            when (imageLoadResult) {
                is ImageLoadResult.Image -> {
                    prevUiState.copy(
                        image = HotelDetailsUiState.Content.Image.ImageSource(imageLoadResult.path)
                    )
                }
                ImageLoadResult.FailedToLoad -> {
                    prevUiState.copy(
                        image = HotelDetailsUiState.Content.Image.NoImage
                    )
                }
                ImageLoadResult.ImageNotFound -> {
                    prevUiState.copy(
                        image = HotelDetailsUiState.Content.Image.NoImage
                    )
                }
            }

        } else prevUiState
    }
}