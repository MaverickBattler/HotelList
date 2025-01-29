package com.mokhnatkinkirill.hotellist.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import com.mokhnatkinkirill.hotellist.BuildConfig
import com.mokhnatkinkirill.hotellist.di.mainModule

class HotelListApp: Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initKoin()
        super.onCreate()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HotelListApp)
            modules(listOf(mainModule))
        }
    }
}