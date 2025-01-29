package com.mokhnatkinkirill.hotellist.hotel_list.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mokhnatkinkirill.hotellist.databinding.FragmentHotelListBinding
import com.mokhnatkinkirill.hotellist.flow_util.collectWithLifecycle
import com.mokhnatkinkirill.hotellist.hotel_list.ui.adapter.HotelListAdapter
import com.mokhnatkinkirill.hotellist.hotel_list.ui.event.HotelListUiEvent
import com.mokhnatkinkirill.hotellist.hotel_list.ui.state.HotelListUiState
import com.mokhnatkinkirill.hotellist.hotel_list.ui.viewmodel.HotelListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HotelListFragment : Fragment() {

    private var _binding: FragmentHotelListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HotelListViewModel>()

    private var hotelListAdapter: HotelListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupHotelList()
        viewModel.uiState.collectWithLifecycle(viewLifecycleOwner) { uiState ->
            renderUiState(uiState)
        }
        viewModel.uiEvent.collectWithLifecycle(viewLifecycleOwner) { uiEvent ->
            handleUiEvent(uiEvent)
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.updateHotelListData()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupHotelList() {
        val onItemClickListener = { hotelId: Int ->
            val action = HotelListFragmentDirections.actionHotelListToHotelInfo(hotelId)
            findNavController().navigate(action)
        }
        hotelListAdapter = HotelListAdapter(onItemClickListener)
        (binding.hotelListRecyclerview.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.hotelListRecyclerview.setHasFixedSize(true)
        binding.hotelListRecyclerview.adapter = hotelListAdapter
        binding.hotelListRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun renderUiState(uiState: HotelListUiState) {
        hotelListAdapter?.submitList(uiState.appsInfoList)
        binding.swiperefresh.isRefreshing = uiState.isSwipeRefreshRefreshing
        binding.progressBar.isVisible = uiState.progressBarVisible
        binding.messageTextview.isVisible = uiState.messageToShowVisible
        binding.messageTextview.text = uiState.messageToShow
    }

    private fun handleUiEvent(event: HotelListUiEvent) {
        when (event) {
            is HotelListUiEvent.ShowToast -> {
                Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}