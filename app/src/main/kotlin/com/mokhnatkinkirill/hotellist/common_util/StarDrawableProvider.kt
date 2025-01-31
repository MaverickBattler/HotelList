package com.mokhnatkinkirill.hotellist.common_util

import androidx.annotation.DrawableRes
import com.mokhnatkinkirill.hotellist.R

class StarDrawableProvider {

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