package com.hantash.weatherapp.di.activiry

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hantash.weatherapp.model.network.WeatherAPI
import com.hantash.weatherapp.model.repo.LocationRepository
import com.hantash.weatherapp.model.repo.WeatherRepository
import com.hantash.weatherapp.model.utils.DialogNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun activity() = activity

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun fusedLocationProvider(activity: AppCompatActivity) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(activity)
    }

    @Provides
    @ActivityScope
    fun dialogNavigator(fragmentManager: FragmentManager): DialogNavigator {
        return DialogNavigator(fragmentManager)
    }

    @Provides
    @ActivityScope
    fun weatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepository(weatherAPI)
    }

    @Provides
    @ActivityScope
    fun locationRepository(fusedLocationProviderClient: FusedLocationProviderClient): LocationRepository {
        return LocationRepository(fusedLocationProviderClient)
    }

}