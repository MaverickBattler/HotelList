package com.mokhnatkinkirill.hotellist.di

import com.mokhnatkinkirill.hotellist.common_util.StarDrawableProvider
import com.mokhnatkinkirill.hotellist.details.presentation.viewmodel.HotelDetailsViewModel
import com.mokhnatkinkirill.hotellist.data.network.HotelInfoApiService
import com.mokhnatkinkirill.hotellist.data.network.RetrofitClient
import com.mokhnatkinkirill.hotellist.data.network.mapper.HotelInfoMapper
import com.mokhnatkinkirill.hotellist.data.network.mapper.HotelListInfoMapper
import com.mokhnatkinkirill.hotellist.data.repository.HotelInfoRepositoryImpl
import com.mokhnatkinkirill.hotellist.data.repository.HotelListInfoRepositoryImpl
import com.mokhnatkinkirill.hotellist.details.domain.interactor.GetHotelInfoInteractor
import com.mokhnatkinkirill.hotellist.hotel_list.domain.interactor.GetHotelListInfoInteractor
import com.mokhnatkinkirill.hotellist.details.domain.repository.HotelInfoRepository
import com.mokhnatkinkirill.hotellist.details.presentation.mapper.HotelDetailsUiStateMapper
import com.mokhnatkinkirill.hotellist.hotel_list.domain.repository.HotelListInfoRepository
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.mapper.HotelListUiStateMapper
import com.mokhnatkinkirill.hotellist.hotel_list.presentation.viewmodel.HotelListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel {
        HotelListViewModel(
            getHotelListInfoInteractor = get(),
            hotelListUiStateMapper = get(),
        )
    }

    viewModel { params ->
        HotelDetailsViewModel(
            hotelId = params.get(),
            getHotelInfoInteractor = get(),
            hotelDetailsUiStateMapper = get(),
        )
    }

    factory {
        HotelListInfoRepositoryImpl(
            hotelListInfoMapper = get(),
            hotelInfoApiService = get(),
        )
    }

    factory<HotelListInfoRepository> {
        get<HotelListInfoRepositoryImpl>()
    }

    factory {
        HotelInfoRepositoryImpl(
            hotelInfoMapper = get(),
            hotelInfoApiService = get(),
        )
    }

    factory<HotelInfoRepository> {
        get<HotelInfoRepositoryImpl>()
    }

    factory {
        HotelInfoMapper()
    }

    factory {
        HotelListInfoMapper()
    }

    factory {
        HotelListUiStateMapper(
            application = get(),
            starDrawableProvider = get()
        )
    }

    factory {
        HotelDetailsUiStateMapper(
            application = get(),
            starDrawableProvider = get(),
        )
    }

    factory {
        GetHotelListInfoInteractor(
            hotelListInfoRepository = get()
        )
    }

    factory {
        GetHotelInfoInteractor(
            hotelInfoRepository = get()
        )
    }

    factory<HotelInfoApiService> {
        RetrofitClient.hotelInfoApiService
    }

    factory {
        StarDrawableProvider()
    }
}