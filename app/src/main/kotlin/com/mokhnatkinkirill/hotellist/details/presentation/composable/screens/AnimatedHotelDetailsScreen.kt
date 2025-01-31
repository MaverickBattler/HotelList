package com.mokhnatkinkirill.hotellist.details.presentation.composable.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState

@Composable
fun AnimatedHotelDetailsContent(
    hotel: HotelDetailsUiState.Content,
    onMapClicked: (lat: String, lon: String) -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    // Animate the appearance of hotel details on first composition
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 300), label = "AnimateHotelDetailsAppear"
    )

    Box(modifier = Modifier.alpha(alpha)) {
        HotelDetailsContent(hotel = hotel, onMapClicked = onMapClicked)
    }
}