package com.example.fluxsample.flux

import androidx.lifecycle.ViewModel
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.fetchweather.FetchWeatherStore

class MainViewModel(
    dispatcher: Dispatcher,
) : ViewModel() {
    val weatherStore = FetchWeatherStore(dispatcher)
}
