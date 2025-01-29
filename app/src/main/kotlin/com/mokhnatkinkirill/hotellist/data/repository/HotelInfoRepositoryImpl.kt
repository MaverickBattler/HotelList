package com.mokhnatkinkirill.hotellist.data.repository

import com.mokhnatkinkirill.hotellist.data.network.HotelInfoApiService
import com.mokhnatkinkirill.hotellist.data.network.mapper.HotelInfoMapper
import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfo
import com.mokhnatkinkirill.hotellist.details.domain.repository.HotelInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotelInfoRepositoryImpl(
    private val hotelInfoMapper: HotelInfoMapper,
    private val hotelInfoApiService: HotelInfoApiService,
): HotelInfoRepository {

    override suspend fun getHotelInfo(hotelId: Int): HotelInfo = withContext(Dispatchers.IO) {
        val hotelInfo = hotelInfoApiService.getHotelInfo(hotelId)
        hotelInfoMapper.mapHotelInfo(hotelInfo)
    }
}