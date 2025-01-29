package com.mokhnatkinkirill.hotellist.hotel_list.domain.model

sealed interface HotelListResult {

    data class Info(
        val hotelListInfo: List<HotelListInfo>
    ): HotelListResult

    data object NetworkError: HotelListResult
}

data class HotelListInfo(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val suitesAvailable: Int
)