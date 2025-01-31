package com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter

import androidx.annotation.DrawableRes

data class HotelListModel(
    val id: Int,
    val name: String,
    val address: CharSequence,
    @DrawableRes val star1: Int,
    @DrawableRes val star2: Int,
    @DrawableRes val star3: Int,
    @DrawableRes val star4: Int,
    @DrawableRes val star5: Int,
    val starsValue: String,
    val distance: CharSequence,
    val suitesAvailable: CharSequence
)