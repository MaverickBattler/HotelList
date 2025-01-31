package com.mokhnatkinkirill.hotellist.data.network.mapper

import com.mokhnatkinkirill.hotellist.data.network.HOTEL_IMAGE_ENDPOINT
import com.mokhnatkinkirill.hotellist.data.network.model.HotelInfoDto
import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfoResult

class HotelInfoMapper {

    fun mapHotelInfoResult(
        hotelInfoDto: HotelInfoDto?,
    ): HotelInfoResult {
        if (hotelInfoDto == null) return HotelInfoResult.NetworkError
        val image = if (hotelInfoDto.image.isNullOrEmpty()) {
            HotelInfoResult.Image.NoImage
        } else {
            HotelInfoResult.Image.Url("$HOTEL_IMAGE_ENDPOINT${hotelInfoDto.image}")
        }
        val suitesAvailability = hotelInfoDto.suitesAvailability.split(
            SUITES_AVAILABILITY_LIST_SEPARATOR
        ).filter { it.isNotEmpty() }
        return HotelInfoResult.HotelInfo(
            id = hotelInfoDto.id,
            name = hotelInfoDto.name,
            address = hotelInfoDto.address,
            stars = hotelInfoDto.stars,
            distance = hotelInfoDto.distance,
            image = image,
            suitesAvailability = suitesAvailability,
            lat = hotelInfoDto.lat,
            lon = hotelInfoDto.lon,
        )
    }

    private companion object {
        const val SUITES_AVAILABILITY_LIST_SEPARATOR = ":"
    }
}