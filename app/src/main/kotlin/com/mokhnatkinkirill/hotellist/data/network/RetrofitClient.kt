package com.mokhnatkinkirill.hotellist.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private const val HOTEL_INFO_BASE_URL =
        "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val hotelInfoApiService: HotelInfoApiService =
        Retrofit.Builder()
            .baseUrl(HOTEL_INFO_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HotelInfoApiService::class.java)
}