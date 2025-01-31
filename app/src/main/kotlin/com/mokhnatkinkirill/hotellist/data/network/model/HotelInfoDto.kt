package com.mokhnatkinkirill.hotellist.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HotelInfoDto(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
    val image: String?,
    @Json(name = "suites_availability")
    val suitesAvailability: String,
    val lat: Double,
    val lon: Double,
)