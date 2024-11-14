package com.example.fluxsample.flux.showweather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.fluxsample.flux.common.Action
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.common.Store
import com.example.fluxsample.flux.fetchweather.FetchWeatherAction

class ShowWeatherStore(
    dispatcher: Dispatcher,
) : Store(dispatcher) {
    private var _uiState: MutableState<ShowWeatherUiState> = mutableStateOf(ShowWeatherUiState.Initial)
    val uiState: State<ShowWeatherUiState>
        get() = _uiState

    override fun onDispatch(payload: Action) {
        when (payload) {
            is FetchWeatherAction -> {
                when (payload) {
                    is FetchWeatherAction.Initial -> {
                        _uiState.value = ShowWeatherUiState.Initial
                    }
                    is FetchWeatherAction.Loading -> {
                        _uiState.value = ShowWeatherUiState.Loading
                    }
                    is FetchWeatherAction.Success -> {
                        _uiState.value = ShowWeatherUiState.Success(payload.payload)
                    }
                    is FetchWeatherAction.Failure -> {
                        _uiState.value = ShowWeatherUiState.Failure(payload.payload)
                    }
                }
            }
//                その他のアクション
//                is OtherAction -> {
//                    when (payload) {}
//                        is OtherAction.Initial -> { }
//                    　　 省略
//                }
        }
    }
}
