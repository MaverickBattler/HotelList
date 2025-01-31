package com.mokhnatkinkirill.hotellist.details.domain.repository

import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfoResult

interface HotelInfoRepository {

    suspend fun getHotelInfo(hotelId: Int): HotelInfoResult
}