package com.hantash.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import com.hantash.weatherapp.di.activiry.ActivityModule
import com.hantash.weatherapp.di.app.AppModule
import com.hantash.weatherapp.di.app.DaggerAppComponent

open class BaseActivity: AppCompatActivity() {

    private val appComponent get() =
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()

    val activityComponent get() = appComponent.newActivityComponent(ActivityModule(this))

}