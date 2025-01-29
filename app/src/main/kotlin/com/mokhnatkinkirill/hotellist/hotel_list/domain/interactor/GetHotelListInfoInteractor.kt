package com.mokhnatkinkirill.hotellist.hotel_list.domain.interactor

import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.domain.repository.HotelListInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHotelListInfoInteractor(private val hotelListInfoRepository: HotelListInfoRepository) {

    suspend fun getHotelListInfo(): HotelListResult = withContext(Dispatchers.IO) {
        hotelListInfoRepository.getHotelListInfo()
    }
}