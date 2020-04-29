package com.whatiamdoing.weather.presentation.presenter

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import com.whatiamdoing.weather.di.App
import com.whatiamdoing.weather.model.WeatherData
import com.whatiamdoing.weather.presentation.ui.MainView
import com.whatiamdoing.weather.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun onFirstViewAttach() {
        viewState.getCurrentLocation()
    }

    fun loadData(lat: Float, long: Float) {
        CoroutineScope(Dispatchers.Main).launch {
            viewState.showProgressBar()
            when (val result = loadWeather(lat, long)) {
                null -> viewState.onError()
                else -> viewState.setData(result)
            }
            viewState.hideProgressBar()
           sharedPreference.saveLocation(lat, long)
        }
    }

    private suspend fun loadWeather(lat: Float, long: Float): CurrentWeatherEntity? =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            WeatherData.getDataFromApi(lat, long)
        }
}