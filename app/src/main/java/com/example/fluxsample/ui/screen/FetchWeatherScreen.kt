package com.example.fluxsample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fluxsample.flux.MainViewModel
import com.example.fluxsample.flux.fetchweather.FetchWeatherActionCreator
import com.example.fluxsample.flux.fetchweather.FetchWeatherUiState
import com.example.fluxsample.ui.common.IndeterminateCircularIndicator
import kotlinx.coroutines.launch

@Composable
fun FetchWeatherScreen(
    modifier: Modifier,
    weatherActionCreator: FetchWeatherActionCreator,
    onNavigate: () -> Unit,
    viewModel: MainViewModel,
) {
    val scope = rememberCoroutineScope()
    val fetchWeatherStore = viewModel.fetchWeatherStore

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            scope.launch {
                weatherActionCreator.fetchWeatherByCityName("Tokyo")
            }
        }) {
            Text(
                text = "天気を取得",
                fontSize = 30.sp,
            )
        }

        Spacer(modifier = Modifier.padding(50.dp))

        when (fetchWeatherStore.uiState.value) {
            is FetchWeatherUiState.Initial -> {
                Text(
                    text = "まだ天気を取得していません",
                    fontSize = 25.sp,
                )
            }
            is FetchWeatherUiState.Loading -> {
                IndeterminateCircularIndicator()
            }
            is FetchWeatherUiState.Success -> {
                Text(
                    text = "東京の現在の天気：${(fetchWeatherStore.uiState.value as FetchWeatherUiState.Success).weatherDescription}",
                    fontSize = 30.sp,
                )
            }
            is FetchWeatherUiState.Failure -> {
                Text(
                    text = (fetchWeatherStore.uiState.value as FetchWeatherUiState.Failure).errorMessage,
                    fontSize = 30.sp,
                )
            }
        }
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = {
                onNavigate()
            },
        ) {
            Text(
                text = "画面を移動",
                fontSize = 30.sp,
            )
        }
    }
}
