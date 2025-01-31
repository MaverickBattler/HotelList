package com.mokhnatkinkirill.hotellist.hotel_list.presentation.model

import androidx.annotation.StringRes
import com.mokhnatkinkirill.hotellist.R

enum class SortType(@StringRes val displayStringId: Int) {
    DEFAULT(R.string.default_),
    SUITES_AMOUNT(R.string.free_suites),
    DISTANCE_FROM_TOWN_CENTER(R.string.distance_from_center)
}