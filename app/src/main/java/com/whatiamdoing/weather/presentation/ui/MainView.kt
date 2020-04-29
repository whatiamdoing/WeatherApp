package com.whatiamdoing.weather.presentation.ui

import com.whatiamdoing.weather.data.CurrentWeatherEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(data: CurrentWeatherEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onError()

    @StateStrategyType(SkipStrategy::class)
    fun getCurrentLocation()

}