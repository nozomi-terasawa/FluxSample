package com.example.fluxsample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fluxsample.flux.MainViewModel
import com.example.fluxsample.flux.showweather.ShowWeatherUiState
import com.example.fluxsample.ui.common.IndeterminateCircularIndicator

@Composable
fun ShowWeatherScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
) {
    val showWeatherStore = viewModel.showWeatherStore

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(50.dp))

        when (showWeatherStore.uiState.value) {
            is ShowWeatherUiState.Initial -> {
                Text(
                    text = "まだ天気が取得されていません",
                    fontSize = 25.sp,
                )
            }
            is ShowWeatherUiState.Loading -> {
                IndeterminateCircularIndicator()
            }
            is ShowWeatherUiState.Success -> {
                Text(
                    text =
                        when ((showWeatherStore.uiState.value as ShowWeatherUiState.Success).weatherDescription) {
                            "Clear" -> "洗濯物がよく乾きます！"
                            "Clouds" -> "曇り空が続きます。。"
                            "Rain" -> "傘を忘れずに！！"
                            else -> "天気が取得されました"
                        },
                    fontSize = 30.sp,
                )
            }
            is ShowWeatherUiState.Failure -> {
                Text(
                    text = (showWeatherStore.uiState.value as ShowWeatherUiState.Failure).errorMessage,
                    fontSize = 30.sp,
                )
            }
        }
    }
}
