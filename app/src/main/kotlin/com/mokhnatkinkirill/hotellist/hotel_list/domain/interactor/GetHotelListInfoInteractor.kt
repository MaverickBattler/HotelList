package com.mokhnatkinkirill.hotellist.hotel_list.domain.interactor

import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.SortBy
import com.mokhnatkinkirill.hotellist.hotel_list.domain.repository.HotelListInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHotelListInfoInteractor(private val hotelListInfoRepository: HotelListInfoRepository) {

    suspend fun getHotelListInfo(sortBy: SortBy): HotelListResult = withContext(Dispatchers.IO) {
        val hotelListResult = hotelListInfoRepository.getHotelListInfo()
        if (hotelListResult is HotelListResult.Info) {
            val newHotelListInfo = when (sortBy) {
                SortBy.NO_SORT -> hotelListResult.hotelListInfo
                SortBy.SUITES_AMOUNT -> hotelListResult.hotelListInfo.sortedByDescending {
                    it.suitesAvailable
                }
                SortBy.DISTANCE_FROM_TOWN_CENTER -> hotelListResult.hotelListInfo.sortedBy {
                    it.distance
                }
            }
            return@withContext hotelListResult.copy(hotelListInfo = newHotelListInfo)
        } else {
            return@withContext hotelListResult
        }
    }
}