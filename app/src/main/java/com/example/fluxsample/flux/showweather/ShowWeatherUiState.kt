package com.example.fluxsample.flux.showweather

sealed class ShowWeatherUiState {
    data object Initial : ShowWeatherUiState()

    data object Loading : ShowWeatherUiState()

    data class Success(
        val weatherDescription: String,
    ) : ShowWeatherUiState()

    data class Failure(
        val errorMessage: String,
    ) : ShowWeatherUiState()
}
