package com.example.fluxsample.flux.fetchweather

sealed class FetchWeatherUiState {
    data object Initial : FetchWeatherUiState()

    data object Loading : FetchWeatherUiState()

    data class Success(
        val weatherDescription: String,
    ) : FetchWeatherUiState()

    data class Failure(
        val errorMessage: String,
    ) : FetchWeatherUiState()
}
