package com.whatiamdoing.weather.di

import android.app.Application
import com.whatiamdoing.weather.di.module.ContextModule
import com.whatiamdoing.weather.di.module.NetworkModule
import com.whatiamdoing.weather.di.module.SharedPreferencesModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = with(DaggerAppComponent.builder()) {
            sharedPreferencesModule(SharedPreferencesModule)
            contextModule(ContextModule(applicationContext))
            networkModule(NetworkModule)
        }.build()
    }
}