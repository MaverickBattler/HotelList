package com.mokhnatkinkirill.hotellist.data.network.mapper

import com.mokhnatkinkirill.hotellist.data.network.model.HotelListInfoDto
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListInfo
import com.mokhnatkinkirill.hotellist.hotel_list.domain.model.HotelListResult

class HotelListInfoMapper {

    fun mapHotelListResult(hotelListInfoDtos: List<HotelListInfoDto>?): HotelListResult {
        if (hotelListInfoDtos == null) return HotelListResult.NetworkError
        return HotelListResult.Info(
            hotelListInfo = hotelListInfoDtos.map { hotelListInfoDto ->
                HotelListInfo(
                    id = hotelListInfoDto.id,
                    name = hotelListInfoDto.name,
                    address = hotelListInfoDto.address,
                    stars = hotelListInfoDto.stars,
                    distance = hotelListInfoDto.distance,
                    suitesAvailable = hotelListInfoDto.suitesAvailability.split(
                        SUITES_AVAILABILITY_LIST_SEPARATOR
                    ).map { it.isNotEmpty() }.count()
                )
            }
        )
    }


    private companion object {
        const val SUITES_AVAILABILITY_LIST_SEPARATOR = ":"
    }
}