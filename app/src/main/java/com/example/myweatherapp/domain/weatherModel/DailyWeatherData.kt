package com.example.myweatherapp.domain.weatherModel

import java.time.LocalDateTime

data class DailyWeatherData(
    val time: LocalDateTime,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherType: WeatherType,
    val precipitationProbability: Int
)
