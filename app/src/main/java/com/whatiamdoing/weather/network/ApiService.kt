package com.whatiamdoing.weather.network

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.utils.Constants.Api.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("access_key") key: String = API_KEY,
        @Query("query") query: String
    ): Response<CurrentWeatherEntity>

}