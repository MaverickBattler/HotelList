package com.mokhnatkinkirill.hotellist.details.domain.interactor

import com.mokhnatkinkirill.hotellist.details.domain.repository.HotelImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHotelImageInteractor(
    private val hotelImageRepository: HotelImageRepository,
) {

    suspend fun getHotelImage(imageName: String) = withContext(Dispatchers.IO) {
        hotelImageRepository.getHotelImage(imageName)
    }
}