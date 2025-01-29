package com.mokhnatkinkirill.hotellist.hotel_list.ui.state

import com.mokhnatkinkirill.hotellist.hotel_list.ui.adapter.HotelListModel

data class HotelListUiState(
    val appsInfoList: List<HotelListModel>,
    val progressBarVisible: Boolean,
    val isSwipeRefreshRefreshing: Boolean,
    val messageToShow: String,
) {
    val messageToShowVisible: Boolean
        get() = messageToShow.isNotEmpty() && messageToShow.isNotBlank()
}