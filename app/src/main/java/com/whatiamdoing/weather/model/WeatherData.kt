package com.whatiamdoing.weather.model

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.network.ApiService

object WeatherData {

    suspend fun getDataFromApi(lat: Float, long: Float): CurrentWeatherEntity? =
        try {
            ApiService().getCurrentWeather(query = "$lat,$long").body()
        } catch (t: Exception) {
            null
        }
}