package com.whatiamdoing.weather.di

import com.whatiamdoing.weather.presentation.ui.MainActivity
import com.whatiamdoing.weather.di.module.ContextModule
import com.whatiamdoing.weather.di.module.NetworkModule
import com.whatiamdoing.weather.di.module.SharedPreferencesModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, SharedPreferencesModule::class, ContextModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

}