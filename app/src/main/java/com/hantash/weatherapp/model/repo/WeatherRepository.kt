package com.hantash.weatherapp.model.repo

import com.hantash.weatherapp.model.network.ResultAPI
import com.hantash.weatherapp.model.network.WeatherAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val weatherAPI: WeatherAPI
) {

    suspend fun fetchCurrentWeather(latLng: String): ResultAPI {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherAPI.getCurrentWeather(latLng)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext ResultAPI.SUCCESS(response.body()!!)
                } else {
                    return@withContext ResultAPI.FAILURE
                }
            } catch(t: Throwable) {
                return@withContext ResultAPI.FAILURE
            }


        }
    }

}