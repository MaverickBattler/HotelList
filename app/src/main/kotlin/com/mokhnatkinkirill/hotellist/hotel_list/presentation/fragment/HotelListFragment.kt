package com.mokhnatkinkirill.hotellist.hotel_list.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.common_util.collectWithLifecycle
import com.mokhnatkinkirill.hotellist.databinding.FragmentHotelListBinding
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter.HotelListAdapter
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.adapter.SortingSpinnerAdapter
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.event.HotelListUiEvent
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.model.SortType
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.state.HotelListUiState
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.viewmodel.HotelListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        setupSortingSpinner()
        viewModel.uiState.collectWithLifecycle(viewLifecycleOwner) { uiState ->
            renderUiState(uiState)
        }
        viewModel.uiEvent.collectWithLifecycle(viewLifecycleOwner) { uiEvent ->
            handleUiEvent(uiEvent)
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.updateHotelListData()
        }
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.setOnClickListener {
            binding.hotelListRecyclerview.scrollToPosition(0)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        showSortingSpinner()
        super.onStart()
    }

    override fun onStop() {
        hideSortingSpinner()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        removeSortingSpinner()
        requireActivity().findViewById<Toolbar>(R.id.toolbar).setOnClickListener(null)
        super.onDestroyView()
    }

    private fun setupSortingSpinner() {

        val spinner: Spinner = requireActivity().findViewById(R.id.spinner_sort)

        spinner.isVisible = true

        val sortingOptions = SortType.entries.map {
            requireActivity().getString(it.displayStringId)
        }
        val adapter = SortingSpinnerAdapter(requireActivity(), sortingOptions)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sortType = when (position) {
                    0 -> SortType.DEFAULT
                    1 -> SortType.SUITES_AMOUNT
                    2 -> SortType.DISTANCE_FROM_TOWN_CENTER
                    else -> SortType.DEFAULT
                }
                viewModel.setSortType(sortType)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No-op
            }
        }
    }

    private fun setupHotelList() {
        val onItemClickListener = { hotelId: Int ->
            navigateToHotelDetails(hotelId)
        }
        hotelListAdapter = HotelListAdapter(onItemClickListener)
        (binding.hotelListRecyclerview.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
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
        hotelListAdapter?.submitList(uiState.appsInfoList) {
            if (uiState.shouldScrollToTopAfterSubmittingList) {
                binding.hotelListRecyclerview.scrollToPosition(0)
                viewModel.onScrolledListToTop()
            }
        }
        binding.apply {
            swiperefresh.isRefreshing = uiState.isSwipeRefreshRefreshing
            progressBarWrapper.isVisible = uiState.progressBarVisible
            messageTextview.isVisible = uiState.messageToShowVisible
            messageTextview.text = uiState.messageToShow
        }

        val spinner = requireActivity().findViewById<Spinner>(R.id.spinner_sort)
        val index = SortType.entries.indexOf(uiState.sortingType)
        // Prevent infinite loop
        if (spinner.selectedItemPosition != index) {
            spinner.setSelection(index)
        }
    }

    private fun navigateToHotelDetails(hotelId: Int) {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.hotelList) {
            val action = HotelListFragmentDirections.actionHotelListToHotelInfo(hotelId)
            navController.navigate(action)
        }
    }

    private fun handleUiEvent(event: HotelListUiEvent) {
        when (event) {
            is HotelListUiEvent.ShowToast -> {
                Toast.makeText(requireActivity(), event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideSortingSpinner() {
        val spinner: Spinner = requireActivity().findViewById(R.id.spinner_sort)
        spinner.isVisible = false
    }

    private fun showSortingSpinner() {
        val spinner: Spinner = requireActivity().findViewById(R.id.spinner_sort)
        spinner.isVisible = true
    }

    private fun removeSortingSpinner() {
        val spinner: Spinner = requireActivity().findViewById(R.id.spinner_sort)
        spinner.isVisible = false
        spinner.adapter = null
        spinner.onItemSelectedListener = null
    }
}