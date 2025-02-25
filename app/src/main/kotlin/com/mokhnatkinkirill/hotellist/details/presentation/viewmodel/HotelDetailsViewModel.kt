package com.mokhnatkinkirill.hotellist.details.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokhnatkinkirill.hotellist.details.domain.interactor.GetHotelInfoInteractor
import com.mokhnatkinkirill.hotellist.details.presentation.event.HotelDetailsUiEvent
import com.mokhnatkinkirill.hotellist.details.presentation.mapper.HotelDetailsUiStateMapper
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HotelDetailsViewModel(
    private val hotelId: Int,
    private val getHotelInfoInteractor: GetHotelInfoInteractor,
    private val hotelDetailsUiStateMapper: HotelDetailsUiStateMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HotelDetailsUiState> =
        MutableStateFlow(getInitialState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HotelDetailsUiEvent>()
    val uiEvent: SharedFlow<HotelDetailsUiEvent> = _uiEvent.asSharedFlow()

    private var getHotelInfoJob: Job? = null

    init {
        getNewHotelListData()
    }

    fun updateHotelData() {
        // don't start the new update until the previous one completes as it is probably unnecessary
        if (getHotelInfoJob == null || getHotelInfoJob?.isActive == false) {
            getNewHotelListData()
        }
    }

    fun onOpenMapClicked(lat: String, lon: String) {
        val geoUri = Uri.parse("geo:$lat,$lon?q=$lat,$lon")
        viewModelScope.launch {
            _uiEvent.emit(HotelDetailsUiEvent.OpenMapEvent(geoUri))
        }
    }

    private fun getNewHotelListData() {
        getHotelInfoJob = viewModelScope.launch(Dispatchers.IO) {
            val hotelInfo = getHotelInfoInteractor.getHotelInfo(hotelId)
            _uiState.emit(hotelDetailsUiStateMapper.mapHotelDetailsUiState(hotelInfo))
        }
    }

    private fun getInitialState(): HotelDetailsUiState {
        return HotelDetailsUiState.Loading
    }
}