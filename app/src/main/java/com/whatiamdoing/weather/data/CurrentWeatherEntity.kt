package com.whatiamdoing.weather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntity(
    @SerializedName("location")
    @Expose
    val location: LocationWeather,
    @SerializedName("current")
    @Expose
    val current: CurrentWeather
)