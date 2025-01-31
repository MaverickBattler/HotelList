package com.mokhnatkinkirill.hotellist.details.presentation.mapper

import android.app.Application
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.common_util.StarDrawableProvider
import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfoResult
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState
import java.util.Locale

class HotelDetailsUiStateMapper(
    private val application: Application,
    private val starDrawableProvider: StarDrawableProvider,
) {

    fun mapHotelDetailsUiState(hotelInfoResult: HotelInfoResult): HotelDetailsUiState {
        return when (hotelInfoResult) {
            is HotelInfoResult.HotelInfo -> {
                val star1 = starDrawableProvider.getStarDrawableForStarNumber(
                    1,
                    hotelInfoResult.stars
                )
                val star2 = starDrawableProvider.getStarDrawableForStarNumber(
                    2,
                    hotelInfoResult.stars
                )
                val star3 = starDrawableProvider.getStarDrawableForStarNumber(
                    3,
                    hotelInfoResult.stars
                )
                val star4 = starDrawableProvider.getStarDrawableForStarNumber(
                    4,
                    hotelInfoResult.stars
                )
                val star5 = starDrawableProvider.getStarDrawableForStarNumber(
                    5,
                    hotelInfoResult.stars
                )
                val image = when(hotelInfoResult.image) {
                    HotelInfoResult.Image.NoImage -> {
                        HotelDetailsUiState.Content.Image.NoImage
                    }
                    is HotelInfoResult.Image.Url -> {
                        HotelDetailsUiState.Content.Image.ImageSource(hotelInfoResult.image.url)
                    }
                }
                val suitesAvailabilityList = hotelInfoResult.suitesAvailability.joinToString()
                val suitesAvailability =
                    application.getString(R.string.free_suites_are, suitesAvailabilityList)
                val distance =
                    application.getString(R.string.distance_to_center_is, hotelInfoResult.distance.toString())
                val stars = String.format(Locale.US, "(%.2f)", hotelInfoResult.stars)
                HotelDetailsUiState.Content(
                    id = hotelInfoResult.id,
                    name = hotelInfoResult.name,
                    address = hotelInfoResult.address,
                    star1 = star1,
                    star2 = star2,
                    star3 = star3,
                    star4 = star4,
                    star5 = star5,
                    starsValue = stars,
                    distance = distance,
                    image = image,
                    suitesAvailability = suitesAvailability,
                    lat = hotelInfoResult.lat.toString(),
                    lon = hotelInfoResult.lon.toString(),
                )
            }

            HotelInfoResult.NetworkError -> HotelDetailsUiState.Error(
                message = application.getString(R.string.error_loading_hotel_details)
            )
        }
    }
}