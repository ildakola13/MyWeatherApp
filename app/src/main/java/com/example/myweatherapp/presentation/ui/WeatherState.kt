package com.example.myweatherapp.presentation.ui

import com.example.myweatherapp.domain.weatherModel.DailyWeatherData
import com.example.myweatherapp.domain.weatherModel.WeatherInfo

data class WeatherState (
    val weatherInfo: WeatherInfo? = null,
    val dailyWeather: List<DailyWeatherData>? = null,
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val error: String? = null
)