package com.hantash.weatherapp.di.viewmodel

import com.google.android.gms.location.FusedLocationProviderClient
import com.hantash.weatherapp.model.network.WeatherAPI
import com.hantash.weatherapp.model.repo.LocationRepository
import com.hantash.weatherapp.model.repo.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun locationRepository(fusedLocationProviderClient: FusedLocationProviderClient) : LocationRepository {
        return LocationRepository(fusedLocationProviderClient)
    }

    @Provides
    @ViewModelScoped
    fun weatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepository(weatherAPI)
    }

}