package com.mokhnatkinkirill.hotellist.details.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.mokhnatkinkirill.hotellist.details.presentation.composable.design.HotelListTheme
import com.mokhnatkinkirill.hotellist.details.presentation.composable.screens.HotelDetailsScreen
import com.mokhnatkinkirill.hotellist.details.presentation.event.HotelDetailsUiEvent
import com.mokhnatkinkirill.hotellist.details.presentation.viewmodel.HotelDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HotelDetailsFragment : Fragment() {

    private val args: HotelDetailsFragmentArgs by navArgs()

    private val viewModel by viewModel<HotelDetailsViewModel> {
        parametersOf(args.hotelId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    viewModel.uiEvent.collect { uiEvent ->
                        doOnUiEvent(uiEvent)
                    }
                }
                HotelListTheme {
                    HotelDetailsScreen(
                        state = state,
                        onMapClicked = { lat, lon ->
                            viewModel.onOpenMapClicked(lat, lon)
                        },
                        onRetry = { viewModel.updateHotelData() }
                    )
                }
            }
        }
    }

    private fun doOnUiEvent(uiEvent: HotelDetailsUiEvent) {
        when (uiEvent) {
            is HotelDetailsUiEvent.OpenMapEvent -> {
                val intent = Intent(Intent.ACTION_VIEW, uiEvent.uri)
                requireActivity().startActivity(intent)
            }
        }
    }
}