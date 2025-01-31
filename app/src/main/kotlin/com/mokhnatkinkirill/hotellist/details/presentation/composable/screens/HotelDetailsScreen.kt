package com.mokhnatkinkirill.hotellist.details.presentation.composable.screens

import androidx.compose.runtime.Composable
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState

@Composable
fun HotelDetailsScreen(
    state: HotelDetailsUiState,
    onRetry: () -> Unit,
    onMapClicked: (lat: String, lon: String) -> Unit
) {
    when (state) {
        is HotelDetailsUiState.Content -> {
            HotelDetailsContent(hotel = state, onMapClicked = onMapClicked)
        }

        is HotelDetailsUiState.Error -> {
            ErrorScreen(message = state.message, onRetry = onRetry)
        }

        is HotelDetailsUiState.Loading -> {
            LoadingScreen()
        }
    }
}