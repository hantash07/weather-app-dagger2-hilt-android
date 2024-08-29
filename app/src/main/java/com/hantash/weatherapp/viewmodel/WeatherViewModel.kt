package com.hantash.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hantash.weatherapp.model.data.WeatherResponse
import com.hantash.weatherapp.model.network.ResultAPI
import com.hantash.weatherapp.model.network.WeatherAPI
import com.hantash.weatherapp.model.repo.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    private val _weather = MutableLiveData<ResultAPI>()
    val weatherLiveData: LiveData<ResultAPI> = _weather

    fun fetchCurrentWeather(latLng: String) {
        viewModelScope.launch {
            _weather.value =  weatherRepository.fetchCurrentWeather(latLng)
        }
    }

}