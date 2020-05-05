package com.whatiamdoing.weather.data

data class CurrentWeather(
    val temperature: Int,
    val pressure: Int,
    val weather_descriptions: List<String>,
    val observation_time: String,
    val humidity: Int,
    val wind_speed: Int,
    val cloudcover: Int,
    val weather_code: Int,
    val feelslike: Int,
    val visibility: Int
)