package com.mokhnatkinkirill.hotellist.details.presentation.composable.screens

import androidx.compose.runtime.Composable
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState

@Composable
fun HotelDetailsScreen(
    state: HotelDetailsUiState,
    onMapClicked: (lat: String, lon: String) -> Unit,
    onRetry: () -> Unit
) {
    when (state) {
        is HotelDetailsUiState.Loading -> {
            LoadingScreen()
        }

        is HotelDetailsUiState.Error -> {
            ErrorScreen(message = state.message, onRetry = onRetry)
        }

        is HotelDetailsUiState.Content -> {
            AnimatedHotelDetailsContent(hotel = state, onMapClicked = onMapClicked)
        }
    }
}