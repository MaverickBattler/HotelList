package com.mokhnatkinkirill.hotellist.data.network

import com.mokhnatkinkirill.hotellist.data.network.model.HotelInfoDto
import com.mokhnatkinkirill.hotellist.data.network.model.HotelListInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelInfoApiService {

    @GET("0777.json")
    suspend fun getHotelList(): List<HotelListInfoDto>

    @GET("{id}.json")
    suspend fun getHotelInfo(@Path("id") hotelId: Int): HotelInfoDto
}