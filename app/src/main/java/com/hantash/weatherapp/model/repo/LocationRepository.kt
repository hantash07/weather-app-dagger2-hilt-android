package com.hantash.weatherapp.model.repo

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    private val _location = MutableLiveData<Location?>()
    val locationLiveData: LiveData<Location?> = _location

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(): LiveData<Location?> {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                _location.value = location
            }
            .addOnFailureListener {
                Log.d("app-debug", "Failed to get location: ${it.message}")
            }
        return _location
    }

}