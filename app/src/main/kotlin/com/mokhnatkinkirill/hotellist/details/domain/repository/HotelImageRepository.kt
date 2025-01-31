package com.mokhnatkinkirill.hotellist.details.domain.repository

import com.mokhnatkinkirill.hotellist.data.network.model.ImageLoadResult

interface HotelImageRepository {

    suspend fun getHotelImage(imageName: String): ImageLoadResult
}