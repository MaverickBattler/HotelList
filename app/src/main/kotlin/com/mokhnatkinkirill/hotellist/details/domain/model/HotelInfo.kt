package com.mokhnatkinkirill.hotellist.details.domain.model

data class HotelInfo(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val image: String,
    val suitesAvailability: List<String>,
    val lat: Double,
    val lon: Double,
)