package com.hantash.weatherapp.model.network

import com.hantash.weatherapp.model.data.WeatherResponse

sealed class ResultAPI {
    class SUCCESS(val weatherResponse: WeatherResponse) : ResultAPI()
    object FAILURE : ResultAPI()
}