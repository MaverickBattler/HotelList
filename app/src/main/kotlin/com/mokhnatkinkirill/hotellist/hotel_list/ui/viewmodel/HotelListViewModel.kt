package com.mokhnatkinkirill.hotellist.hotel_list.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.hotel_list.domain.interactor.GetHotelListInfoInteractor
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.ui.event.HotelListUiEvent
import com.mokhnatkinkirill.hotellist.hotel_list.ui.mapper.HotelListUiStateMapper
import com.mokhnatkinkirill.hotellist.hotel_list.ui.state.HotelListUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HotelListViewModel(
    private val getHotelListInfoInteractor: GetHotelListInfoInteractor,
    private val hotelListUiStateMapper: HotelListUiStateMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HotelListUiState> = MutableStateFlow(getInitialState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HotelListUiEvent>()
    val uiEvent: SharedFlow<HotelListUiEvent> = _uiEvent.asSharedFlow()

    private var getHotelInfoJob: Job? = null

    init {
        getNewHotelListData(userInitiated = false)
    }

    fun updateHotelListData() {
        getNewHotelListData(userInitiated = true)
    }

    private fun getNewHotelListData(userInitiated: Boolean) {
        // don't start the new update until the previous one completes as it is probably unnecessary
        if (getHotelInfoJob == null || getHotelInfoJob?.isActive == false) {
            if (userInitiated) {
                _uiState.update { prevUiState ->
                    prevUiState.copy(isSwipeRefreshRefreshing = true)
                }
            }
            getHotelInfoJob = viewModelScope.launch {
                val hotelListRequestResult = getHotelListInfoInteractor.getHotelListInfo()
                if (hotelListRequestResult is HotelListResult.NetworkError) {
                    _uiEvent.emit(HotelListUiEvent.ShowToast(R.string.network_error_message))
                }
                _uiState.update { prevUiState ->
                    hotelListUiStateMapper.mapHotelListUiState(prevUiState, hotelListRequestResult)
                }
            }
        }
    }

    private fun getInitialState(): HotelListUiState {
        return HotelListUiState(
            appsInfoList = emptyList(),
            progressBarVisible = true,
            isSwipeRefreshRefreshing = false,
            messageToShow = "",
        )
    }
}