package com.whatiamdoing.weather.utils

import android.content.SharedPreferences
import com.whatiamdoing.weather.utils.Constants.SharedPref.PREF_LAT
import com.whatiamdoing.weather.utils.Constants.SharedPref.PREF_LONG
import javax.inject.Inject

class SharedPreference @Inject constructor(private val SharedPreferences: SharedPreferences) {

    fun getLocation(): Pair<Float, Float> =
        Pair(
        SharedPreferences.getFloat(PREF_LAT, 0f),
        SharedPreferences.getFloat(PREF_LONG, 0f)
    )

    fun saveLocation(lat: Float, long: Float) =
        with(SharedPreferences.edit()) {
            putFloat(PREF_LAT, lat)
            putFloat(PREF_LONG, long)
            apply()
        }
}