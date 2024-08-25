package com.hantash.weatherapp.model.network

import com.hantash.weatherapp.model.data.WeatherResponse
import com.hantash.weatherapp.model.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/v1/current.json?key=${Constant.API_KEY_WEATHER}")
    suspend fun getCurrentWeather(@Query("q") latLng: String): Response<WeatherResponse>
}