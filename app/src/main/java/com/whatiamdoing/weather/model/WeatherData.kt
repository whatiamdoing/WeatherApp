package com.whatiamdoing.weather.model

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.network.ApiService
import javax.inject.Inject

object WeatherData {

    @Inject
    lateinit var apiService: ApiService

    suspend fun getDataFromApi(lat: Float, long: Float): CurrentWeatherEntity? =
        try {
            apiService.getCurrentWeather(query = "$lat,$long").body()
        } catch (t: Exception) {
            null
        }
}