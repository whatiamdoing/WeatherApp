package com.whatiamdoing.weather.model

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.di.App
import com.whatiamdoing.weather.network.ApiService

object WeatherData {

    private var apiService: ApiService = App.appComponent.getApiService()

    suspend fun getDataFromApi(lat: Float, long: Float): CurrentWeatherEntity? =
        apiService.getCurrentWeather(query = "$lat,$long").body()
}