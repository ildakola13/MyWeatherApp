package com.example.myweatherapp.domain.weatherModel

import java.time.LocalDateTime

data class HourlyWeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType
)
