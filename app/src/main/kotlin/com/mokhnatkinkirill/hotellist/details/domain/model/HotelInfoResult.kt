package com.mokhnatkinkirill.hotellist.details.domain.model

sealed interface HotelInfoResult {

    data class HotelInfo(
        val id: Int,
        val name: String,
        val address: String,
        val stars: Double,
        val distance: Double,
        val imageName: String?,
        val suitesAvailability: List<String>,
        val lat: Double,
        val lon: Double,
    ) : HotelInfoResult

    data object NetworkError : HotelInfoResult
}
