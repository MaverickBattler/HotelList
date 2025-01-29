package com.mokhnatkinkirill.hotellist.details.domain.interactor

import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfo
import com.mokhnatkinkirill.hotellist.details.domain.repository.HotelInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHotelInfoInteractor(
    private val hotelInfoRepository: HotelInfoRepository,
) {

    suspend fun getHotelInfo(hotelId: Int): HotelInfo = withContext(Dispatchers.IO) {
        hotelInfoRepository.getHotelInfo(hotelId)
    }
}