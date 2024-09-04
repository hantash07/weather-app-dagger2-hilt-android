package com.hantash.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import com.hantash.weatherapp.MainApplication
import com.hantash.weatherapp.di.activiry.ActivityModule
import com.hantash.weatherapp.di.app.AppModule
import com.hantash.weatherapp.di.app.DaggerAppComponent

open class BaseActivity: AppCompatActivity() {

    private val appComponent get() = (application as MainApplication).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponent()
            .activity(this)
            .fragmentManager(this.supportFragmentManager)
            .activityModule(ActivityModule)
            .build()
    }

}