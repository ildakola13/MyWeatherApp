package com.example.myweatherapp.presentation.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.domain.locationModel.City
import com.example.myweatherapp.presentation.theme.DarkBlue
import com.example.myweatherapp.presentation.theme.DeepBlue
import com.example.myweatherapp.presentation.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            WeatherAppTheme {
                val state by viewModel.state.collectAsState()
                val city by viewModel.locationCity.collectAsState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue),
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkBlue)
                    ) {
                        WeatherScreen(viewModel, state, city, innerPadding)

                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        if (state.isSearching) {
                            SearchLocation(
                                viewModel, innerPadding,
                                backgroundColor = DarkBlue,
                                textColor = Color.White
                            )
                        }
                        state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    state: WeatherState,
    city: City,
    innerPadding: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .background(DarkBlue),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            LocationCard(
                city = city,
                viewModel = viewModel,
                backgroundColor = DeepBlue
            )
        }
        item {
            WeatherCard(
                state = state,
                city = city,
                backgroundColor = DeepBlue
            )
        }
        item {
            HourlyForecast(
                state = state,
                backgroundColor = DeepBlue
            )
        }
        item {
            DailyForecast(
                state = state,
                backgroundColor = DeepBlue
            )
        }
    }

}