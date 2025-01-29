package com.mokhnatkinkirill.hotellist.hotel_list.ui.mapper

import android.app.Application
import android.text.SpannableStringBuilder
import androidx.annotation.DrawableRes
import androidx.core.text.bold
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.ui.adapter.HotelListModel
import com.mokhnatkinkirill.hotellist.hotel_list.ui.state.HotelListUiState

class HotelListUiStateMapper(
    private val application: Application,
) {

    fun mapHotelListUiState(
        prevUiState: HotelListUiState,
        hotelListResult: HotelListResult
    ): HotelListUiState {
        return when (hotelListResult) {
            is HotelListResult.NetworkError -> {
                val messageToShow = if (prevUiState.appsInfoList.isEmpty()) {
                    application.getString(R.string.nothing_to_show)
                } else {
                    ""
                }
                prevUiState.copy(
                    progressBarVisible = false,
                    isSwipeRefreshRefreshing = false,
                    messageToShow = messageToShow
                )
            }

            is HotelListResult.Info -> {
                val messageToShow = if (hotelListResult.hotelListInfo.isEmpty()) {
                    application.getString(R.string.nothing_to_show)
                } else {
                    ""
                }
                val addressPrefix = application.getString(R.string.address_is)
                val distanceToPrefix = application.getString(R.string.distance_to)
                val distanceToPostfix = application.getString(R.string.kilometers_postfix)
                val freeSuitesPrefix = application.getString(R.string.free_suites)
                prevUiState.copy(
                    appsInfoList = hotelListResult.hotelListInfo.map {
                        val star1Drawable = getStarDrawableForStarNumber(1, it.stars)
                        val star2Drawable = getStarDrawableForStarNumber(2, it.stars)
                        val star3Drawable = getStarDrawableForStarNumber(3, it.stars)
                        val star4Drawable = getStarDrawableForStarNumber(4, it.stars)
                        val star5Drawable = getStarDrawableForStarNumber(5, it.stars)
                        val addressBuilder = SpannableStringBuilder().append(addressPrefix)
                        val addressString = addressBuilder.bold {
                            append(it.address)
                        }
                        val distanceBuilder = SpannableStringBuilder().append(distanceToPrefix)
                        val distanceString = distanceBuilder.bold {
                            append(it.distance.toString())
                        }.append(distanceToPostfix)
                        val freeSuitesBuilder = SpannableStringBuilder().append(freeSuitesPrefix)
                        val freeSuites = freeSuitesBuilder.bold {
                            append(it.suitesAvailable.toString())
                        }
                        HotelListModel(
                            id = it.id,
                            name = it.name,
                            address = addressString,
                            star1 = star1Drawable,
                            star2 = star2Drawable,
                            star3 = star3Drawable,
                            star4 = star4Drawable,
                            star5 = star5Drawable,
                            distance = distanceString,
                            suitesAvailable = freeSuites,
                        )
                    },
                    progressBarVisible = false,
                    isSwipeRefreshRefreshing = false,
                    messageToShow = messageToShow,
                )
            }
        }
    }

    @DrawableRes
    fun getStarDrawableForStarNumber(number: Int, stars: Double): Int {
        val offset = number - 1
        return when {
            stars <= offset + 0.25 -> R.drawable.star_empty
            stars <= offset + 0.75 -> R.drawable.star_half
            else -> R.drawable.star_full
        }
    }
}