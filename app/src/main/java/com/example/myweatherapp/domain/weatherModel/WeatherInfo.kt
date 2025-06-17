package com.example.myweatherapp.domain.weatherModel

data class WeatherInfo(
    val last24HWeatherData: List<HourlyWeatherData>,
    val currentWeatherData: HourlyWeatherData?
)
