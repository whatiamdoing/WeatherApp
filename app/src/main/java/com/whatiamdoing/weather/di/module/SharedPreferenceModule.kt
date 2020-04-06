package com.whatiamdoing.weather.di.module

import android.content.Context
import com.whatiamdoing.weather.utils.Constants.SharedPref.PREF_KEY_WEATHER
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences(PREF_KEY_WEATHER, Context.MODE_PRIVATE)!!

}