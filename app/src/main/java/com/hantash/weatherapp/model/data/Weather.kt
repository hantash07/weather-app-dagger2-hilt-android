package com.hantash.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("temp_c") val temperatureCel: Double,
    @SerializedName("temp_f") val temperatureFar: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("feelslike_c") val feelLikeCel: Double,
    @SerializedName("feelslike_f") val feelLikeFar: Double,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("condition") val condition: Condition,
)

data class Condition(
    @SerializedName("code") val code: Int,
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String,
)
