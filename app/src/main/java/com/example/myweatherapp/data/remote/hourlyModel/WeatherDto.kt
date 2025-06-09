package com.example.myweatherapp.data.remote.hourlyModel

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val hourlyWeatherData: HourlyWeatherDataDto
)
