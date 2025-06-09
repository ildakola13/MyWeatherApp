package com.example.myweatherapp.domain.weatherModel

data class WeatherInfo(
    val weeklyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val last24HWeatherData: List<HourlyWeatherData>,
    val currentWeatherData: HourlyWeatherData?
)
