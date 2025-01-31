package com.mokhnatkinkirill.hotellist.hotel_list.presentation.state

import com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter.HotelListModel
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.model.SortType

data class HotelListUiState(
    val appsInfoList: List<HotelListModel>,
    val progressBarVisible: Boolean,
    val isSwipeRefreshRefreshing: Boolean,
    val messageToShow: String,
    val sortingType: SortType,
    val shouldScrollToTopAfterSubmittingList: Boolean,
) {
    val messageToShowVisible: Boolean
        get() = messageToShow.isNotEmpty() && messageToShow.isNotBlank()
}