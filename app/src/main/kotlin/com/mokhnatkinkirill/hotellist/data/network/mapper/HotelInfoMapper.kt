package com.mokhnatkinkirill.hotellist.data.network.mapper

import com.mokhnatkinkirill.hotellist.data.network.model.HotelInfoDto
import com.mokhnatkinkirill.hotellist.details.domain.model.HotelInfo

class HotelInfoMapper {

    fun mapHotelInfo(hotelInfoDto: HotelInfoDto): HotelInfo {
        return HotelInfo(
            id = hotelInfoDto.id,
            name = hotelInfoDto.name,
            address = hotelInfoDto.address,
            stars = hotelInfoDto.stars,
            distance = hotelInfoDto.distance,
            image = hotelInfoDto.image,
            suitesAvailability = hotelInfoDto.suitesAvailability.split(
                SUITES_AVAILABILITY_LIST_SEPARATOR
            ),
            lat = hotelInfoDto.lat,
            lon = hotelInfoDto.lon,
        )
    }

    private companion object {
        const val SUITES_AVAILABILITY_LIST_SEPARATOR = ":"
    }
}