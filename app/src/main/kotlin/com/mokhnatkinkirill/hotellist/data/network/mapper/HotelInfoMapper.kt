package com.mokhnatkinkirill.hotellist.data.network.mapper

import com.mokhnatkinkirill.hotellist.data.network.model.HotelInfoDto
import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfoResult

class HotelInfoMapper {

    fun mapHotelInfoResult(
        hotelInfoDto: HotelInfoDto?,
    ): HotelInfoResult {
        if (hotelInfoDto == null) return HotelInfoResult.NetworkError
        val imageName = if (hotelInfoDto.image.isNullOrEmpty()) null else hotelInfoDto.image
        val suitesAvailability = hotelInfoDto.suitesAvailability.split(
            SUITES_AVAILABILITY_LIST_SEPARATOR
        ).filter { it.isNotEmpty() }
        return HotelInfoResult.HotelInfo(
            id = hotelInfoDto.id,
            name = hotelInfoDto.name,
            address = hotelInfoDto.address,
            stars = hotelInfoDto.stars,
            distance = hotelInfoDto.distance,
            imageName = imageName,
            suitesAvailability = suitesAvailability,
            lat = hotelInfoDto.lat,
            lon = hotelInfoDto.lon,
        )
    }

    private companion object {
        const val SUITES_AVAILABILITY_LIST_SEPARATOR = ":"
    }
}