package com.mokhnatkinkirill.hotellist.details.presentation.state

import androidx.annotation.DrawableRes

sealed interface HotelDetailsUiState {

    data class Content(
        val id: Int,
        val name: String,
        val address: String,
        @DrawableRes val star1: Int,
        @DrawableRes val star2: Int,
        @DrawableRes val star3: Int,
        @DrawableRes val star4: Int,
        @DrawableRes val star5: Int,
        val starsValue: String,
        val distance: String,
        val image: Image,
        val suitesAvailability: String,
        val lat: String,
        val lon: String,
    ) : HotelDetailsUiState {

        sealed interface Image {

            data object NoImage: Image

            data object ImageLoading: Image

            data class ImageSource(val imagePath: String): Image
        }
    }

    data class Error(
        val message: String
    ) : HotelDetailsUiState

    data object Loading : HotelDetailsUiState
}
