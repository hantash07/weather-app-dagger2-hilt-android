package com.hantash.weatherapp

import android.app.Application
import com.hantash.weatherapp.di.app.AppModule
import com.hantash.weatherapp.di.app.DaggerAppComponent

class MainApplication: Application() {

    val appComponent get() =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

}