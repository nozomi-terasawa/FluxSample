package com.example.fluxsample.flux

import androidx.lifecycle.ViewModel
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.fetchweather.FetchWeatherStore
import com.example.fluxsample.flux.showweather.ShowWeatherStore

class MainViewModel(
    dispatcher: Dispatcher,
) : ViewModel() {
    val fetchWeatherStore = FetchWeatherStore(dispatcher) // 天気取得ViewModel
    val showWeatherStore = ShowWeatherStore(dispatcher) // 天気表示ViewModel
}
