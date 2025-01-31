package com.mokhnatkinkirill.hotellist.hotel_list.presentation.event

import androidx.annotation.StringRes

sealed interface HotelListUiEvent {

    data class ShowToast(@StringRes val message: Int) : HotelListUiEvent
}