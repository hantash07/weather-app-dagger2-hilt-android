package com.hantash.weatherapp.di.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hantash.weatherapp.di.app.AppScope
import com.hantash.weatherapp.model.network.WeatherAPI
import com.hantash.weatherapp.model.repo.LocationRepository
import com.hantash.weatherapp.model.repo.WeatherRepository
import com.hantash.weatherapp.model.utils.DialogNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun appCompatActivity(activity: Activity): AppCompatActivity = activity as AppCompatActivity

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

    @Provides
    @ActivityScoped
    fun dialogNavigator(fragmentManager: FragmentManager): DialogNavigator {
        return DialogNavigator(fragmentManager)
    }

}