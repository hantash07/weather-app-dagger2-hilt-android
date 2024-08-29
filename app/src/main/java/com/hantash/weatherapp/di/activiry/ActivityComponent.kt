package com.hantash.weatherapp.di.activiry

import com.hantash.weatherapp.view.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}