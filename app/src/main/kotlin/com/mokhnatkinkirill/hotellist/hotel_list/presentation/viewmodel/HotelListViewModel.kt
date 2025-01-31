package com.mokhnatkinkirill.hotellist.hotel_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.hotel_list.domain.interactor.GetHotelListInfoInteractor
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.SortBy
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.event.HotelListUiEvent
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.mapper.HotelListUiStateMapper
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.model.SortType
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.state.HotelListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        getNewHotelListData(
            shouldShow = ShouldShow.LOADING_INDICATOR
        )
    }

    fun onScrolledListToTop() {
        _uiState.update { prevUiState ->
            prevUiState.copy(shouldScrollToTopAfterSubmittingList = false)
        }
    }

    fun updateHotelListData() {
        // don't start the new update until the previous one completes as it is probably unnecessary
        if (getHotelInfoJob == null || getHotelInfoJob?.isActive == false) {
            getNewHotelListData(
                shouldShow = ShouldShow.SWIPE_REFRESH_INDICATOR
            )
        }
    }

    fun setSortType(sortType: SortType) {
        val currentSortType = _uiState.value.sortingType
        if (currentSortType != sortType) {
            _uiState.update { prevUiState ->
                prevUiState.copy(sortingType = sortType)
            }
            // cancel previous update because this one is with new parameters
            getHotelInfoJob?.cancel()
            getNewHotelListData(shouldShow = ShouldShow.NOTHING, shouldScrollListToTop = true)
        }
    }

    private fun getNewHotelListData(
        shouldShow: ShouldShow,
        shouldScrollListToTop: Boolean = false
    ) {
        val sortType = _uiState.value.sortingType
        if (shouldShow == ShouldShow.SWIPE_REFRESH_INDICATOR) {
            _uiState.update { prevUiState ->
                prevUiState.copy(isSwipeRefreshRefreshing = true)
            }
        } else if (shouldShow == ShouldShow.LOADING_INDICATOR) {
            _uiState.update { prevUiState ->
                prevUiState.copy(progressBarVisible = true)
            }
        }
        getHotelInfoJob = viewModelScope.launch(Dispatchers.IO) {
            // even when showing nothing, if getting data takes a long time, it's best to show an indicator
            val setLoadingJob = if (shouldShow == ShouldShow.NOTHING) {
                launch {
                    delay(DELAY_BEFORE_SHOWING_PROGRESS)
                    _uiState.update { prevUiState ->
                        prevUiState.copy(isSwipeRefreshRefreshing = true)
                    }
                }
            } else null
            val hotelListRequestResult = getHotelListInfoInteractor.getHotelListInfo(
                sortBy = when (sortType) {
                    SortType.DEFAULT -> SortBy.NO_SORT
                    SortType.SUITES_AMOUNT -> SortBy.SUITES_AMOUNT
                    SortType.DISTANCE_FROM_TOWN_CENTER -> SortBy.DISTANCE_FROM_TOWN_CENTER
                }
            )
            var shouldScrollToTop = shouldScrollListToTop
            if (hotelListRequestResult is HotelListResult.NetworkError) {
                shouldScrollToTop = false
                _uiEvent.emit(HotelListUiEvent.ShowToast(R.string.network_error_message))
            }
            setLoadingJob?.cancelAndJoin()
            _uiState.update { prevUiState ->
                hotelListUiStateMapper.mapHotelListUiState(
                    prevUiState,
                    hotelListRequestResult,
                    shouldScrollToTop,
                )
            }
        }
    }

    private fun getInitialState(): HotelListUiState {
        return HotelListUiState(
            appsInfoList = emptyList(),
            progressBarVisible = true,
            isSwipeRefreshRefreshing = false,
            messageToShow = "",
            sortingType = SortType.DEFAULT,
            shouldScrollToTopAfterSubmittingList = false,
        )
    }

    private enum class ShouldShow {
        LOADING_INDICATOR, SWIPE_REFRESH_INDICATOR, NOTHING
    }

    private companion object {
        const val DELAY_BEFORE_SHOWING_PROGRESS = 1000L
    }
}