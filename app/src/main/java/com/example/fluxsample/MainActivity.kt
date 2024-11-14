package com.example.fluxsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fluxsample.flux.MainViewModel
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.fetchweather.FetchWeatherActionCreator
import com.example.fluxsample.flux.fetchweather.FetchWeatherRepositoryImpl
import com.example.fluxsample.flux.fetchweather.FetchWeatherUiState
import com.example.fluxsample.ui.common.IndeterminateCircularIndicator
import com.example.fluxsample.ui.theme.FluxSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var dispatcher: Dispatcher
    @Inject lateinit var fetchWeatherRepository: FetchWeatherRepositoryImpl
    @Inject lateinit var weatherActionCreator: FetchWeatherActionCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FluxSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FluxSampleApp(
                        modifier = Modifier.padding(innerPadding),
                        weatherActionCreator = weatherActionCreator,
                        dispatcher = dispatcher,
                    )
                }
            }
        }
    }
}

@Composable
fun FluxSampleApp(
    modifier: Modifier,
    weatherActionCreator: FetchWeatherActionCreator,
    dispatcher: Dispatcher,
    viewModel: MainViewModel =
        viewModel {
            MainViewModel(dispatcher)
        },
) {
    val scope = rememberCoroutineScope()
    val weatherStore = viewModel.weatherStore

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

        when (weatherStore.uiState.value) {
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
                    text = "東京の現在の天気：${(weatherStore.uiState.value as FetchWeatherUiState.Success).weatherDescription}",
                    fontSize = 30.sp,
                )
            }
            is FetchWeatherUiState.Failure -> {
                Text(
                    text = (weatherStore.uiState.value as FetchWeatherUiState.Failure).errorMessage,
                    fontSize = 30.sp,
                )
            }
        }
    }
}
