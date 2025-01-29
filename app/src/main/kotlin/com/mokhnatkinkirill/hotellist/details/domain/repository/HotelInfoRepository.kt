package com.mokhnatkinkirill.hotellist.details.domain.repository

import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfo

interface HotelInfoRepository {

    suspend fun getHotelInfo(hotelId: Int): HotelInfo
}