package com.hantash.weatherapp.di.app

import com.hantash.weatherapp.di.activiry.ActivityComponent
import com.hantash.weatherapp.di.activiry.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent

}