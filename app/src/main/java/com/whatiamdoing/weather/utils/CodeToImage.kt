package com.whatiamdoing.weather.utils

import com.whatiamdoing.weather.R

fun weatherCodeToImage(weatherCode: Int): Int = when (weatherCode) {
    113 -> R.drawable.ic_clear
    116 -> R.drawable.ic_partly_cloudy
    260, 248, 122, 119 -> R.drawable.ic_cloudy
    143 -> R.drawable.ic_mist
    296, 293, 284, 281, 266, 263, 176, 299, 302, 305, 308, 311, 314, 359 -> R.drawable.ic_drizzle
    326, 179, 182, 185 -> R.drawable.ic_less_snow
    200 -> R.drawable.ic_thunder
    230, 227, 317, 320, 323, 329, 332, 335, 338, 350, 395, 368, 371, 374, 377  -> R.drawable.ic_snow
    365, 362, 356, 353 -> R.drawable.ic_rain
    386, 389, 392 -> R.drawable.ic_rain_thunder
    else -> R.drawable.ic_null
}