package com.mokhnatkinkirill.hotellist.data.repository

import com.mokhnatkinkirill.hotellist.data.network.HotelInfoApiService
import com.mokhnatkinkirill.hotellist.data.network.mapper.HotelListInfoMapper
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult
import com.mokhnatkinkirill.hotellist.hotel_list.domain.repository.HotelListInfoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotelListInfoRepositoryImpl(
    private val hotelListInfoMapper: HotelListInfoMapper,
    private val hotelInfoApiService: HotelInfoApiService,
) : HotelListInfoRepository {

    override suspend fun getHotelListInfo(): HotelListResult = withContext(Dispatchers.IO) {
        val hotelList = try {
            hotelInfoApiService.getHotelList()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            null
        }
        hotelListInfoMapper.mapHotelListResult(hotelList)
    }
}