package com.mokhnatkinkirill.hotellist.hotel_list.presentation.mapper

import android.app.Application
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.common_util.StarDrawableProvider
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter.HotelListModel
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.state.HotelListUiState
import java.util.Locale

class HotelListUiStateMapper(
    private val application: Application,
    private val starDrawableProvider: StarDrawableProvider,
) {

    fun mapHotelListUiState(
        prevUiState: HotelListUiState,
        hotelListResult: HotelListResult,
        shouldScrollToTop: Boolean,
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
                    messageToShow = messageToShow,
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
                val distanceToPostfix = application.getString(R.string.meters_postfix)
                val freeSuitesPrefix = application.getString(R.string.free_suites_amt)
                prevUiState.copy(
                    appsInfoList = hotelListResult.hotelListInfo.map {
                        val star1Drawable =
                            starDrawableProvider.getStarDrawableForStarNumber(1, it.stars)
                        val star2Drawable =
                            starDrawableProvider.getStarDrawableForStarNumber(2, it.stars)
                        val star3Drawable =
                            starDrawableProvider.getStarDrawableForStarNumber(3, it.stars)
                        val star4Drawable =
                            starDrawableProvider.getStarDrawableForStarNumber(4, it.stars)
                        val star5Drawable =
                            starDrawableProvider.getStarDrawableForStarNumber(5, it.stars)
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
                        val starsValue = String.format(Locale.US, "(%.2f)", it.stars)
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
                            starsValue = starsValue,
                        )
                    },
                    progressBarVisible = false,
                    isSwipeRefreshRefreshing = false,
                    messageToShow = messageToShow,
                    shouldScrollToTopAfterSubmittingList = shouldScrollToTop,
                )
            }
        }
    }
}