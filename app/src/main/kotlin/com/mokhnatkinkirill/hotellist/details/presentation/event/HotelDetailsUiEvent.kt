package com.mokhnatkinkirill.hotellist.details.presentation.event

import android.net.Uri
import androidx.annotation.StringRes

sealed interface HotelDetailsUiEvent {

    data class ShowToast(@StringRes val message: Int) : HotelDetailsUiEvent

    data class OpenMapEvent(val uri: Uri) : HotelDetailsUiEvent
}