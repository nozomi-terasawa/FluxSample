package com.example.fluxsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fluxsample.flux.MainViewModel
import com.example.fluxsample.flux.common.Dispatcher
import com.example.fluxsample.flux.fetchweather.FetchWeatherActionCreator
import com.example.fluxsample.flux.fetchweather.FetchWeatherRepositoryImpl
import com.example.fluxsample.ui.screen.FetchWeatherScreen
import com.example.fluxsample.ui.screen.ShowWeatherScreen
import com.example.fluxsample.ui.theme.FluxSampleTheme
import dagger.hilt.android.AndroidEntryPoint
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
                    FluxApplication(
                        modifier = Modifier.padding(innerPadding),
                        dispatcher = dispatcher,
                        weatherActionCreator = weatherActionCreator,
                        navController = rememberNavController(),
                    )
                }
            }
        }
    }
}

enum class Screen(
    title: String,
) {
    FETCH(title = "FetchWeather"),
    SHOW(title = "ShowWeather"),
}

@Composable
fun FluxApplication(
    modifier: Modifier = Modifier,
    dispatcher: Dispatcher,
    weatherActionCreator: FetchWeatherActionCreator,
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel =
        viewModel {
            MainViewModel(
                dispatcher = dispatcher,
            )
        },
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen =
        Screen.valueOf(
            backStackEntry?.destination?.route ?: Screen.FETCH.name,
        )

    Scaffold { innerPadding ->
        NavHost(
            modifier =
                modifier
                    .padding(innerPadding),
            navController = navController,
            startDestination = "fetch",
        ) {
            composable(route = Screen.FETCH.name) {
                FetchWeatherScreen(
                    modifier = Modifier.fillMaxSize(),
                    weatherActionCreator = weatherActionCreator,
                    onNavigate = {
                        navController.navigate(Screen.SHOW.name)
                    },
                    viewModel = mainViewModel,
                )
            }
            composable(route = Screen.SHOW.name) {
                ShowWeatherScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = mainViewModel,
                )
            }
        }
    }
}
