package com.mokhnatkinkirill.hotellist.hotel_list.domain.repository

import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult

interface HotelListInfoRepository {

    suspend fun getHotelListInfo(): HotelListResult
}