package com.whatiamdoing.weather.di

import com.whatiamdoing.weather.presentation.ui.MainActivity
import com.whatiamdoing.weather.di.module.ContextModule
import com.whatiamdoing.weather.di.module.NetworkModule
import com.whatiamdoing.weather.di.module.SharedPreferencesModule
import com.whatiamdoing.weather.model.WeatherData
import com.whatiamdoing.weather.network.ApiService
import com.whatiamdoing.weather.presentation.presenter.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    SharedPreferencesModule::class,
    ContextModule::class,
    NetworkModule::class
])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(weatherData: WeatherData)
    fun getApiService(): ApiService

}