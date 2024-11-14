package com.example.fluxsample.flux.fetchweather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.fluxsample.flux.common.Action
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.common.Store

class FetchWeatherStore(
    dispatcher: Dispatcher,
) : Store(dispatcher) {
    private var _uiState: MutableState<FetchWeatherUiState> = mutableStateOf(FetchWeatherUiState.Initial)
    val uiState: State<FetchWeatherUiState>
        get() = _uiState

    override fun onDispatch(payload: Action) {
        when (payload) {
            is FetchWeatherAction.Initial -> {
                _uiState.value = FetchWeatherUiState.Initial
            }
            is FetchWeatherAction.Loading -> {
                _uiState.value = FetchWeatherUiState.Loading
            }
            is FetchWeatherAction.Success -> {
                _uiState.value = FetchWeatherUiState.Success(payload.payload)
            }
            is FetchWeatherAction.Failure -> {
                _uiState.value = FetchWeatherUiState.Failure(payload.payload)
            }
        }
    }
}
