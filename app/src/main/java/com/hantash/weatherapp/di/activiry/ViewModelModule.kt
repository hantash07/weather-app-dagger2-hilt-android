package com.hantash.weatherapp.di.activiry

import androidx.lifecycle.ViewModel
import com.hantash.weatherapp.viewmodel.LocationViewModel
import com.hantash.weatherapp.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule { //All viewModel will be added here

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun weatherViewModel(viewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun locationViewModel(locationViewModel: LocationViewModel): ViewModel
}