package com.hantash.weatherapp.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hantash.weatherapp.model.repo.LocationRepository
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {
    val locationLiveData: LiveData<Location?> = locationRepository.locationLiveData

    fun fetchCurrentLocation() {
        locationRepository.fetchCurrentLocation()
    }
}