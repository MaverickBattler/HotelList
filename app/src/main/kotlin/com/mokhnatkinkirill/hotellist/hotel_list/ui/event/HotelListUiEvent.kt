package com.mokhnatkinkirill.hotellist.hotel_list.ui.event

import androidx.annotation.StringRes

sealed interface HotelListUiEvent {

    data class ShowToast(@StringRes val message: Int) : HotelListUiEvent
}