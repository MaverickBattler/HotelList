package com.mokhnatkinkirill.hotellist.details.presentation.event

import android.net.Uri

sealed interface HotelDetailsUiEvent {

    data class OpenMapEvent(val uri: Uri) : HotelDetailsUiEvent
}