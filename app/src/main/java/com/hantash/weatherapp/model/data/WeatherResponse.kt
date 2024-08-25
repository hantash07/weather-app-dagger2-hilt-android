package com.hantash.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current") val weather: Weather,
    @SerializedName("location") val location: Location,
)
